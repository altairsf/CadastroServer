package cadastroserver;

import controller.UsuarioJpaController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Usuario;

/**
 *
 * @author Altair
 */
public class CadastroServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

        UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);

        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("Servidor aguardando conexoes na porta 4321...");
            Socket socket = serverSocket.accept();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            String login = (String) in.readObject();
            String senha = (String) in.readObject();
            String mensagem = (String) in.readObject();

            System.out.println("login=" + login + "   senha=" + senha);
            System.out.println("mensagem=" + mensagem);
            out.writeObject("GRAVANDO NO BANCO - login=" + login + " senha=" + login);
            out.flush();

            List<Usuario> usuariosList = ctrlUsu.findUsuarioEntities();
            int tamanhoLista = usuariosList.size();
            
            System.out.println("tamnho=" + tamanhoLista);
            for (Usuario usuario : usuariosList) {
                System.out.println("login=" + usuario.getLogin());
            }

            System.out.println("GRAVANDO NO BANCO - login=" + login + " senha=" + login);
            Usuario userTeste = new Usuario((tamanhoLista + 1));
            userTeste.setLogin(login + (tamanhoLista + 1));
            userTeste.setSenha(senha + (tamanhoLista + 1));

            ctrlUsu.create(userTeste);
        }
    }

}
