package ColectionsListaseArrays.test;
import ColectionsListaseArrays.domain.ClientDomain;
import ColectionsListaseArrays.dao.ClientDAO;
import ColectionsListaseArrays.dao.UserDAO;
import ColectionsListaseArrays.domain.UserDomain;

import java.util.List;
import java.util.Optional;

public class DAOTest {
    public static UserDAO user = new UserDAO();
    public static ClientDAO client = new ClientDAO();
    public static void main(String[] args) {
        testUserDAO();
        testClientDAO();
    }
    public static void testUserDAO(){
        System.out.println("===UserDAOTest(start)===");
        UserDomain user1 = new UserDomain(1,"Daniel", 19);
        UserDomain user2 = new UserDomain(2,"Junior", 32);
        UserDomain user3 = new UserDomain(3,"Wanderlei", 65);

        user.save(user1);
        user.saveAll(user2, user3);
        assert user.count() == 3 : "ERRO: count esperado 3";

        UserDomain user4 = new UserDomain(4,"Alice",25);
        UserDomain user5 = new UserDomain(5,"Lucas",40);
        boolean savedAll = user.saveAll(user4, user5);
        assert savedAll : "ERRO: savedAll falhou";
        assert user.count() == 5 : "ERRO: count esperado 5";

        Optional<UserDomain> found = user.find(d -> d.getId().equals(1));
        assert found.isPresent() : "ERRO: id 1 não encontrado";

        Optional<UserDomain> notFound = user.find(d -> d.getId().equals(99));
        assert notFound.isEmpty() : "ERRO: usuário inexistente encontrado";

        UserDomain user1Up = new UserDomain(1,"Daniel Updated",20);
        UserDomain updated = user.update(1, user1Up);
        assert updated.getName().equals("Daniel Updated") : "ERRO: falha no update";

        boolean deleted = user.delete(user2);
        assert deleted : "ERRO: delete falhou";
        assert user.find(d -> d.getId().equals(2)).isEmpty() : "ERRO: usuário 2 continua existindo";

        UserDomain deleted2 = new UserDomain(99,"Fake",0);
        boolean isDeleted = user.delete(deleted2);
        assert !isDeleted : "ERRO: deletou um objeto inexistente";

        List<UserDomain> allUsers = user.findAll();
        assert allUsers.size() == 4 : "ERRO: retorno errado do findAll";

        System.out.println("===UserDAOTest(end)===\n");
    }
    public static void testClientDAO(){
        System.out.println("===ClientDAOTest(start)===");
        ClientDomain client1 = new ClientDomain("A","Daniel","daniel@gmail.com");
        ClientDomain client2 = new ClientDomain("B","Junior","junior@gmail.com");
        ClientDomain client3 = new ClientDomain("C","Wanderlei","wanderlei@gmail.com");

        client.save(client1);
        client.save(client2);
        client.save(client3);
        assert client.count() == 3 : "ERRO: count esperado 3";

        Optional<ClientDomain> found = client.find(d -> d.getEmail().equals("daniel@gmail.com"));
        assert found.isPresent() : "ERRO: client1 não encontrado";

        Optional<ClientDomain> notFound = client.find(d -> d.getEmail().equals("levi@gmail.com"));
        assert notFound.isEmpty() : "ERRO: email inexistente encontrado";

        ClientDomain client1Up = new ClientDomain("A","Daniel Updated","daniel@gmail.com");
        ClientDomain updated = client.update("A",client1Up);
        assert updated.getName().equals("Daniel Updated") : "ERRO: update de client1 falhou";

        boolean deleted = client.delete(client2);
        assert deleted : "ERRO: delete de client2 falhou";
        assert client.find(d -> d.getEmail().equals("junior@gmail.com")).isEmpty() : "ERRO: client2 ainda existente";

        ClientDomain deleted2 = new ClientDomain("X","Luan","luan@gmail.com");
        boolean isDeleted = client.delete(deleted2);
        assert !isDeleted : "ERRO: deletou um objeto inexistente";

        List<ClientDomain> allClients = client.findAll();
        assert allClients.size() == 2 : "ERRO: findAl retornou tamanho errado";

        System.out.println("===ClientDAOTest(end)===\n");
    }
}
