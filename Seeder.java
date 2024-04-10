public class Seeder {
    public void initialize() {
        //Authors
        /// Author 1
        Author author1 = new Author();
        Date date1 = new Date();
        date1.setBirthDate("6/March/1927");
        Profile profile1 = new Profile("Gabriel", "Marquez", date1);
        author1.setProfile(profile1);
        date1.setPublishDate("30/May/1967");
        Book book1 = new Book("Cien años de soledad", "9867", author1, date1, true);
        author1.setBooks(book1);
        AuthorRepository.authors.add(author1);
        BookRepository.books.add(book1);

        /// Author 2
        Author author2 = new Author();
        Date date2 = new Date();
        date2.setBirthDate("30/July/1965");
        Profile profile2 = new Profile("J.K", "Rowling", date2);
        author2.setProfile(profile2);
        date2.setPublishDate("26/June /1997");
        Book book2 = new Book("Harry Potter y la piedra filosofal", "2763", author2, date2, true);
        author2.setBooks(book2);
        AuthorRepository.authors.add(author2);
        BookRepository.books.add(book2);

        /////////////////////////////////////////////////////////////////////////

        //Clients
        /// Client 1

        Date date3 = new Date();
        date3.setBirthDate("07/May/2014");
        Profile profile3 = new Profile("Sebastian", "Damian", date3);
        Client client1 = new Client(profile3, "Sebas97", "s3b4s");
        ClientRepository.clients.add(client1);


        //Admins
        // SuperADmin
        Date date5 = new Date();
        date5.setBirthDate("22/March/1985");
        Profile profile5 = new Profile("Cristiano", "Ronaldo", date5);
        Administrator superAdministrator = new Administrator(profile5, "cr7", "cr7", true);
        superAdministrator.setRead();
        superAdministrator.setDelete();
        superAdministrator.setWrite();
        AdministratorRepository.administrators.add(superAdministrator);

        // admin1
        Date date6 = new Date();
        date6.setBirthDate("25/July/1990");
        Profile profile6 = new Profile("Juan", "Alberto", date6);
        Administrator administrator = new Administrator(profile6, "wanalberto", "wana", false);
        administrator.setWrite();
        administrator.setRead();
        AdministratorRepository.administrators.add(administrator);
    }
}
