package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import model.Movimento;
import model.MovimentoPK;
import model.Pessoa;
import model.Produto;
import model.Usuario;

public class CadastroThread extends Thread {

    //private ProdutoJpaController ctrl;
    private ProdutoJpaController ctrlProd;
    private UsuarioJpaController ctrlUsu;
    private MovimentoJpaController ctrlMov;
    private PessoaJpaController ctrlPessoa;
    private Socket s1;

    public CadastroThread(ProdutoJpaController ctrlProd, UsuarioJpaController ctrlUsu,
            MovimentoJpaController ctrlMov, PessoaJpaController ctrlPessoa, Socket s1) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
        this.s1 = s1;
    }

    private String getDayTime() {
        LocalDateTime now = LocalDateTime.now();
        String day = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        return day + " " + time;
    }
    
    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream()); ObjectInputStream in = new ObjectInputStream(s1.getInputStream());) {
            //Canais de In/Out
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            //Login e senha
            Usuario usuario = ctrlUsu.findUsuario(login, senha);
            if (usuario == null) {
                out.writeObject("ERRO: Usuário inválido.");
                out.flush();
                s1.close();
                return;
            } else {
                out.writeObject("Usuário conectado com sucesso em " + getDayTime());
                out.flush();
            }

            //Validação
            while (true) {
                String comando = (String) in.readObject();
                if ("L".equalsIgnoreCase(comando)) {
                    List<Produto> produtos = ctrlProd.findProdutoEntities();
                    out.writeObject(produtos);
                    out.flush();
                } else if ("E".equalsIgnoreCase(comando) || "S".equalsIgnoreCase(comando)) {
                    //Criar o movimento
                    Movimento mov = new Movimento();

                    //ID do movimento
                    int idMovimento = (int) in.readObject();

                    mov.setUsuario(usuario);
                    mov.setTipo(comando);

                    //Id da pessoa
                    int idPessoa = (int) in.readObject();
                    Pessoa pessoa = ctrlPessoa.findPessoa(idPessoa);
                    mov.setPessoa(pessoa);

                    // Id do produto
                    int idProduto = (int) in.readObject();
                    Produto produto = ctrlProd.findProduto(idProduto);
                    mov.setProduto(produto);

                    // Quantidade
                    int quantidade = (int) in.readObject();
                    mov.setQuantidade(quantidade);

                    // Valor unitário
                    int valorUnitario = (int) in.readObject();
                    mov.setValorUnitario(valorUnitario);

                    // Montar o PK 
                    MovimentoPK pk = new MovimentoPK(
                        idMovimento,
                        usuario.getIdUsuario(),
                        idProduto,
                        idPessoa
                    );
                    mov.setMovimentoPK(pk);

                    // Persistir o movimento
                    ctrlMov.create(mov);

                    // Atualizar estoque
                    if ("E".equalsIgnoreCase(comando)) {
                        produto.setQuantidade(produto.getQuantidade() + quantidade);
                    } else if ("S".equalsIgnoreCase(comando)) {
                        produto.setQuantidade(produto.getQuantidade() - quantidade);
                    }
                    ctrlProd.edit(produto);

                    out.writeObject("Movimento registrado com sucesso." + getDayTime());
                    out.flush();
                    List<Produto> produtosAtualizados = ctrlProd.findProdutoEntities();
                    out.writeObject(produtosAtualizados);
                    out.flush();

                } else if ("SAIR".equalsIgnoreCase(comando)) {
                    out.writeObject("Encerrando conexão.");
                    out.flush();
                    break;
                } else {
                    out.writeObject("Comando desconhecido.");
                    out.flush();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro na thread: " + e.getMessage());
        } finally {
            try {
                if (!s1.isClosed()) {
                    s1.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
