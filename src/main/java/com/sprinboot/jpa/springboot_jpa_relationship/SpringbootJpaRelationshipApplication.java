package com.sprinboot.jpa.springboot_jpa_relationship;

import com.sprinboot.jpa.springboot_jpa_relationship.entities.*;
import com.sprinboot.jpa.springboot_jpa_relationship.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ClientDetailsRepository clientDetailsRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyRemoveBidireccionalFind();
	}

			//ONE TO ONE UNIDIRECTIONAL EXAMPLES
	@Transactional
	public void oneToOne(){
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Luis","Miguel");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToOneFindById(){
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findById(2L);
		clientOptional.ifPresent(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);

			System.out.println(client);
		});

	}


	//ONE TO ONE BIDIRECTIONAL EXAMPLES
	@Transactional
	public void oneToOneBidireccional(){
		Client client = new Client("Luis","Miguel");

		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToOneBidireccionalFindyById(){
		Optional<Client> clientOptional = clientRepository.findOne(2L);
		clientOptional.ifPresent(client -> {

			ClientDetails clientDetails = new ClientDetails(true, 5000);

			client.setClientDetails(clientDetails);

			clientRepository.save(client);
			System.out.println(client);
		});
	}



			// MANY TO MANY UNIDIRECTIONAL EXAMPLES
	public void manyToMany(){
		Student student1 = new Student("Fabio", "Barreda");
		Student student2 = new Student("Mauricio", "Perez");

		Course course1 = new Course("Matematicas", "Lorenzo");
		Course course2 = new Course("Ingles", "Milusca");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);
	}

	public void manyToManyFindById(){
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).orElseThrow();
		Course course2 = courseRepository.findById(2L).orElseThrow();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);
	}

	public void manyToManyRemoveFind(){
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.orElseThrow();
		Student student2 = studentOptional2.orElseThrow();

		Course course1 = courseRepository.findById(1L).orElseThrow();
		Course course2 = courseRepository.findById(2L).orElseThrow();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(1L);
		if(studentOptionalDb.isPresent()){
			Student studentDataB = studentOptionalDb.orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);

			if(courseOptionalDb.isPresent()){
				Course courseDataB = courseOptionalDb.orElseThrow();
				studentDataB.getCourses().remove(courseDataB);
				studentRepository.save(studentDataB);
				System.out.println(studentDataB);
			}
		}
	}

	public void manyToManyRemove(){
		Student student1 = new Student("Fabio", "Barreda");
		Student student2 = new Student("Mauricio", "Perez");

		Course course1 = new Course("Matematicas", "Lorenzo");
		Course course2 = new Course("Ingles", "Milusca");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(3L);
		if(studentOptionalDb.isPresent()){
			Student studentDataB = studentOptionalDb.orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findById(3L);

			if(courseOptionalDb.isPresent()){
				Course courseDataB = courseOptionalDb.orElseThrow();
				studentDataB.getCourses().remove(courseDataB);
				studentRepository.save(studentDataB);
				System.out.println(studentDataB);
			}
		}
	}



		// MANY TO MANY BIDIRECTIONAL EXAMPLES
	public void manyToManyBidireccional(){
		Student student1 = new Student("Fabio", "Barreda");
		Student student2 = new Student("Mauricio", "Perez");

		Course course1 = new Course("Matematicas", "Lorenzo");
		Course course2 = new Course("Ingles", "Milusca");

//		student1.setCourses(Set.of(course1, course2));
//		student2.setCourses(Set.of(course2));

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);
	}

	public void manyToManyBidireccionalRemove(){
		Student student1 = new Student("Fabio", "Barreda");
		Student student2 = new Student("Mauricio", "Perez");

		Course course1 = new Course("Matematicas", "Lorenzo");
		Course course2 = new Course("Ingles", "Milusca");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(3L);
		if(studentOptionalDb.isPresent()){
			Student studentDataB = studentOptionalDb.orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(3L);

			if(courseOptionalDb.isPresent()){
				Course courseDataB = courseOptionalDb.orElseThrow();
				studentDataB.removeCourse(courseDataB);
				studentRepository.save(studentDataB);
				System.out.println(studentDataB);
			}
		}
	}

	public void manyToManyBidireccionalFindById(){
		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).orElseThrow();
		Course course2 = courseRepository.findOneWithStudents(2L).orElseThrow();

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);
	}

	public void manyToManyRemoveBidireccionalFind(){
		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).orElseThrow();
		Course course2 = courseRepository.findOneWithStudents(2L).orElseThrow();

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1,student2));
		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(1L);
		if(studentOptionalDb.isPresent()){
			Student studentDataB = studentOptionalDb.orElseThrow();
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(1L);

			if(courseOptionalDb.isPresent()){
				Course courseDataB = courseOptionalDb.orElseThrow();
				studentDataB.removeCourse(courseDataB);
				studentRepository.save(studentDataB);
				System.out.println(studentDataB);
			}
		}
	}



			// ONE TO MANY UNIDIRECTIONAL EXAMPLES
	@Transactional
	public void oneToMany(){
		Client client = new Client("Lupita", "Quispe");

		Address address1 = new Address("Pizarro", 407);
		Address address2 = new Address("Cayma", 102);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	public void oneToManyFindById(){
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {

			Address address1 = new Address("Pizarro", 407);
			Address address2 = new Address("Cayma", 102);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);

			client.setAddresses(addresses);

			clientRepository.save(client);
			System.out.println(client);
		});
	}

	@Transactional
	public void removeAddress(){
		Client client = new Client("Lupita", "Quispe");

		Address address1 = new Address("Pizarro", 407);
		Address address2 = new Address("Cayma", 102);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);
		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void removeAddressesFindyById(){
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {

			Address address1 = new Address("Pizarro", 407);
			Address address2 = new Address("Cayma", 102);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);

			client.setAddresses(addresses);

			clientRepository.save(client);
			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findById(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(address2);
				clientRepository.save(c);
				System.out.println(c);
			});

		});
	}



	// MANY TO ONE - ONE TO MANY BIDIRECTIONAL EXAMPLES
	@Transactional
	public void oneToManyInvoiceBidireccional() {
		Client client = new Client("Lupita", "Quispe");

		Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

		Set<Invoice> invoices = new HashSet<>();
		invoices.add(invoice1);
		invoices.add(invoice2);
		client.setInvoices(invoices);

		invoice1.setClient(client);
		invoice2.setClient(client);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToManyInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {

			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			Set<Invoice> invoices = new HashSet<>();
			invoices.add(invoice1);
			invoices.add(invoice2);
			client.setInvoices(invoices);

			invoice1.setClient(client);
			invoice2.setClient(client);

			clientRepository.save(client);

			System.out.println(client);
		});
	}

	@Transactional
	public void removeInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {

			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			Set<Invoice> invoices = new HashSet<>();
			invoices.add(invoice1);
			invoices.add(invoice2);
			client.setInvoices(invoices);

			invoice1.setClient(client);
			invoice2.setClient(client);

			clientRepository.save(client);

			System.out.println(client);
		});

		Optional<Client> optionalClientDb = clientRepository.findOne(1L);
		optionalClientDb.ifPresent(client -> {
			Invoice invoice3 = new Invoice("Compras de oficina", 8000L);
			invoice3.setId(1L);

			Optional<Invoice> invoiceOptional = Optional.of(invoice3); /*invoiceRepository.findById(2L);*/
			invoiceOptional.ifPresent(invoice -> {

				client.removeInvoice(invoice);

				clientRepository.save(client);

				System.out.println(client);
			});
		});
	}

	@Transactional
	public void removeInvoiceBidireccional() {

		Client client= new Client("Ricardo","Tulipan");

			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			Set<Invoice> invoices = new HashSet<>();
			invoices.add(invoice1);
			invoices.add(invoice2);
			client.setInvoices(invoices);

			invoice1.setClient(client);
			invoice2.setClient(client);

			clientRepository.save(client);

			System.out.println(client);

		Optional<Client> optionalClientDb = clientRepository.findOne(3L);
		optionalClientDb.ifPresent(clientDataB -> {
			Invoice invoice3 = new Invoice("Compras de oficina", 8000L);
			invoice3.setId(1L);

			Optional<Invoice> invoiceOptional = Optional.of(invoice3); /*invoiceRepository.findById(2L);*/
			invoiceOptional.ifPresent(invoice -> {

				clientDataB.removeInvoice(invoice);

				clientRepository.save(clientDataB);

				System.out.println(clientDataB);
			});
		});

	}



			// MANY TO ONE UNIDIRECTIONAL EXAMPLES
		@Transactional
	public void manyToOne(){

		Client client = new Client("Jose", "Zegarra");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);

	}

	@Transactional
	public void manyToOneFindByIdClient(){

		Optional<Client> optionalClient = clientRepository.findById(1L);
		if (optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();
			Invoice invoice = new Invoice("Compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}



	}
}
