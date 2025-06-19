package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CadastroServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);
            ProdutoJpaController ctrlProd = new ProdutoJpaController(emf);
            MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
            PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);
            ServerSocket serverSocket = new ServerSocket(4321);
            
            System.out.println("Servidor iniciado na porta 4321...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                CadastroThread thread = new CadastroThread(ctrlProd, ctrlUsu, ctrlMov, ctrlPessoa, clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
