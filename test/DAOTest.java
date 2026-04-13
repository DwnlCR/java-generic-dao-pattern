package dao_pattern.test;

import dao_pattern.exceptions.EmptyStorageException;
import dao_pattern.exceptions.ValidatorException;
import dao_pattern.uservalidator.UserDomainValidator;
import dao_pattern.domain.ClientDomain;
import dao_pattern.dao.ClientDAO;
import dao_pattern.exceptions.EntityNotFoundException;
import dao_pattern.dao.UserDAO;
import dao_pattern.domain.MenuOption;
import dao_pattern.domain.UserDomain;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;



public class DAOTest {

    public static UserDAO users = new UserDAO();
    public static ClientDAO client = new ClientDAO();
    public static UserDomainValidator validator = new UserDomainValidator();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Bem vindo, selecione a operação desejada: ");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar por Id");
            System.out.println("5 - Listar todos");
            System.out.println("6 - Sair");
            var userInput = sc.nextInt();
            var selectedOption = MenuOption.values()[userInput - 1];
            switch (selectedOption){
                case SAVE -> {
                    try{
                        UserDomain saved = users.save(requestToSave());
                        System.out.printf("Usuario %s cadastrado \n", saved);
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally {
                        System.out.println("=================");
                    }
                }
                case UPDATE -> {
                    try{
                        UserDomain updated = requestToUpdate();
                        users.update(updated.getId(), updated);
                        System.out.printf("Usuario %s atualizado \n", updated);
                    }catch (EntityNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    } catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally {
                        System.out.println("=================");
                    }
                }
                case DELETE -> {
                    try{
                        users.deleteById(requestToId());
                        System.out.printf("Usuario deletado \n");
                    }catch (EmptyStorageException | EntityNotFoundException ex){
                        System.out.println(ex.getMessage());
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally {
                        System.out.println("=================");
                    }
                }
                case FIND_BY_ID -> {
                    try{
                       int id = requestToId();
                       UserDomain userFound = users.findByIdOrThrow(id);
                        System.out.println("Usuario com o id " + id + " encontrado\n");
                        System.out.println(userFound);
                    }catch (EntityNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally {
                        System.out.println("=================");
                    }
                }
                case FIND_ALL -> {
                    try{
                        List<UserDomain> user = users.findAll();
                        System.out.println("---Usuarios cadastrados---");
                        user.forEach(System.out::println);
                    }catch (EmptyStorageException | EntityNotFoundException ex){
                        System.out.println(ex.getMessage());
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally {
                        System.out.println("=================");
                    }
                }
                case EXIT -> System.exit(0);
            }
        }
    }

    public static UserDomain requestToSave(){
        sc.nextLine();
        System.out.println("Informe o nome do usuario: ");
        String name = sc.nextLine();
        System.out.println("Informe a idade do usuario: ");
        int age = sc.nextInt();
        return validateInputs(0, name, age);
    }

    public static UserDomain validateInputs(final int id, final String name, final int age){
        UserDomain users = new UserDomain(id, name, age);
        validator.validate(users);
        return users;
    }

    public static UserDomain requestToUpdate(){
        System.out.println("Informe o id do usuario: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Informe o novo nome do usuario: ");
        String name = sc.nextLine();
        System.out.println("Informe a nova idade do usuario: ");
        int age = sc.nextInt();
        return validateInputs(id, name, age);
    }

    public static int requestToId(){
        System.out.println("Informe o id do usuario procurado: ");
        return sc.nextInt();
    }

    public static void testUserDAO() {
        System.out.println("===UserDAOTest(start)===");

        UserDomain user1 = new UserDomain(1, "Daniel", 19);
        UserDomain user2 = new UserDomain(2, "Junior", 32);
        UserDomain user3 = new UserDomain(3, "Wanderlei", 65);

        users.save(user1);
        users.saveAll(user2, user3);
        assert users.count() == 3 : "ERRO: count esperado 3";

        assert users.exists(1) : "ERRO: id 1 deveria existir";
        assert !users.exists(99) : "ERRO: id 99 não deveria existir";

        Optional<UserDomain> found = users.findById(1);
        assert found.isPresent() : "ERRO: findById id 1 não encontrado";
        assert found.get().getName().equals("Daniel") : "ERRO: nome incorreto no findById";

        Optional<UserDomain> notFound = users.findById(99);
        assert notFound.isEmpty() : "ERRO: findById retornou algo para id inexistente";

        Optional<UserDomain> foundByName = users.find(d -> d.getName().equals("Junior"));
        assert foundByName.isPresent() : "ERRO: find por nome falhou";

        UserDomain user1Up = new UserDomain(1, "Daniel Updated", 20);
        UserDomain updated = users.update(1, user1Up);
        assert updated.getName().equals("Daniel Updated") : "ERRO: falha no update";

        try {
            users.update(99, new UserDomain(99, "Fake", 0));
            assert false : "ERRO: deveria ter lançado EntityNotFoundException";
        } catch (EntityNotFoundException e) {
        }

        boolean deletedById = users.deleteById(3);
        assert deletedById : "ERRO: deleteById falhou";
        assert !users.exists(3) : "ERRO: id 3 ainda existe após deleteById";

        boolean deletedByIdFake = users.deleteById(99);
        assert !deletedByIdFake : "ERRO: deleteById retornou true para id inexistente";

        boolean deleted = users.delete(user2);
        assert deleted : "ERRO: delete por objeto falhou";
        assert !users.exists(2) : "ERRO: id 2 ainda existe após delete";

        List<UserDomain> allUsers = users.findAll();
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