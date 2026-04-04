package ColectionsListaseArrays.test;

import ColectionsListaseArrays.domain.ClientDomain;
import ColectionsListaseArrays.dao.ClientDAO;
import ColectionsListaseArrays.dao.EntityNotFoundException;
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

    public static void testUserDAO() {
        System.out.println("===UserDAOTest(start)===");

        UserDomain user1 = new UserDomain(1, "Daniel", 19);
        UserDomain user2 = new UserDomain(2, "Junior", 32);
        UserDomain user3 = new UserDomain(3, "Wanderlei", 65);

        user.save(user1);
        user.saveAll(user2, user3);
        assert user.count() == 3 : "ERRO: count esperado 3";

        assert user.exists(1) : "ERRO: id 1 deveria existir";
        assert !user.exists(99) : "ERRO: id 99 não deveria existir";

        Optional<UserDomain> found = user.findById(1);
        assert found.isPresent() : "ERRO: findById id 1 não encontrado";
        assert found.get().getName().equals("Daniel") : "ERRO: nome incorreto no findById";

        Optional<UserDomain> notFound = user.findById(99);
        assert notFound.isEmpty() : "ERRO: findById retornou algo para id inexistente";

        Optional<UserDomain> foundByName = user.find(d -> d.getName().equals("Junior"));
        assert foundByName.isPresent() : "ERRO: find por nome falhou";

        UserDomain user1Up = new UserDomain(1, "Daniel Updated", 20);
        UserDomain updated = user.update(1, user1Up);
        assert updated.getName().equals("Daniel Updated") : "ERRO: falha no update";

        try {
            user.update(99, new UserDomain(99, "Fake", 0));
            assert false : "ERRO: deveria ter lançado EntityNotFoundException";
        } catch (EntityNotFoundException e) {
        }

        boolean deletedById = user.deleteById(3);
        assert deletedById : "ERRO: deleteById falhou";
        assert !user.exists(3) : "ERRO: id 3 ainda existe após deleteById";

        boolean deletedByIdFake = user.deleteById(99);
        assert !deletedByIdFake : "ERRO: deleteById retornou true para id inexistente";

        boolean deleted = user.delete(user2);
        assert deleted : "ERRO: delete por objeto falhou";
        assert !user.exists(2) : "ERRO: id 2 ainda existe após delete";

        List<UserDomain> allUsers = user.findAll();
        assert allUsers.size() == 1 : "ERRO: findAll retornou tamanho errado";
        try {
            allUsers.add(new UserDomain(10, "Teste", 10));
            assert false : "ERRO: findAll deveria retornar lista imutável";
        } catch (UnsupportedOperationException e) {
        }

        System.out.println("===UserDAOTest(end)===\n");
    }

    public static void testClientDAO() {
        System.out.println("===ClientDAOTest(start)===");

        ClientDomain client1 = new ClientDomain("A", "Daniel", "daniel@gmail.com");
        ClientDomain client2 = new ClientDomain("B", "Junior", "junior@gmail.com");
        ClientDomain client3 = new ClientDomain("C", "Wanderlei", "wanderlei@gmail.com");

        client.save(client1);
        client.save(client2);
        client.save(client3);
        assert client.count() == 3 : "ERRO: count esperado 3";

        assert client.exists("A") : "ERRO: id A deveria existir";
        assert !client.exists("Z") : "ERRO: id Z não deveria existir";

        Optional<ClientDomain> found = client.findById("A");
        assert found.isPresent() : "ERRO: findById id A não encontrado";
        assert found.get().getName().equals("Daniel") : "ERRO: nome incorreto no findById";

        Optional<ClientDomain> notFound = client.findById("Z");
        assert notFound.isEmpty() : "ERRO: findById retornou algo para id inexistente";

        Optional<ClientDomain> foundByEmail = client.find(d -> d.getEmail().equals("junior@gmail.com"));
        assert foundByEmail.isPresent() : "ERRO: find por email falhou";

        ClientDomain client1Up = new ClientDomain("A", "Daniel Updated", "daniel@gmail.com");
        ClientDomain updated = client.update("A", client1Up);
        assert updated.getName().equals("Daniel Updated") : "ERRO: update de client1 falhou";

        boolean deletedById = client.deleteById("C");
        assert deletedById : "ERRO: deleteById falhou";
        assert !client.exists("C") : "ERRO: id C ainda existe após deleteById";

        boolean deleted = client.delete(client2);
        assert deleted : "ERRO: delete de client2 falhou";
        assert !client.exists("B") : "ERRO: id B ainda existe após delete";

        List<ClientDomain> allClients = client.findAll();
        assert allClients.size() == 1 : "ERRO: findAll retornou tamanho errado";

        System.out.println("===ClientDAOTest(end)===\n");
    }
}