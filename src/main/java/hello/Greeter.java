package hello;

/*******************************************************************************
 * Copyright (c) 2016 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.Model;

// example 2
import org.eclipse.rdf4j.model.util.ModelBuilder;

// example 3
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

// example 4
import org.eclipse.rdf4j.model.vocabulary.DC;

// example 5
import org.eclipse.rdf4j.model.BNode;

// example 6
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// example 8
import java.io.InputStream;

/**
 * RDF Tutorial example 01: Building a simple RDF Model using Eclipse RDF4J
 *
 * @author Jeen Broekstra
 */
public class Greeter {
  public String sayHello() {
    return "Hello world!";
  }

  public String example01_RDF4J() {
    String output = "";
    // IRIs, blank nodes and literals.
    ValueFactory vf = SimpleValueFactory.getInstance();

    // We want to reuse this namespace when creating several building blocks.
    String ex = "http://example.org/";

    // Create IRIs for the resources we want to add.
    IRI picasso = vf.createIRI(ex, "Picasso");
    IRI artist = vf.createIRI(ex, "Artist");

    // Create a new, empty Model object.
    Model model = new TreeModel();

    // add our first statement: Picasso is an Artist
    model.add(picasso, RDF.TYPE, artist);

    // second statement: Picasso's first name is "Pablo".
    model.add(picasso, FOAF.FIRST_NAME, vf.createLiteral("Pablo"));

    for (Statement statement: model) {
      output += statement;
    }
    return output;
  }

  public String example02_RDF4J() {
    String output = "";
    ModelBuilder builder = new ModelBuilder();
    Model model = builder
      	.setNamespace("ex", "http://example.org/")
		  .subject("ex:Picasso")
				.add(RDF.TYPE, "ex:Artist")
				.add(FOAF.FIRST_NAME, "Pablo")
		  .build();

    for (Statement statement: model) {
      output += statement;
    }

    return output;
  }

  public String example03_RDF4J() {
    String output = "";
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		// Create a new RDF model containing information about the painting "The Potato Eaters"
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:PotatoEaters")
					// this painting was created on April 1, 1885
					.add("ex:creationDate", vf.createLiteral("1885-04-01T00:00:00Z", XMLSchema.DATETIME))
					// You can also pass in a Java Date object directly: 
				    //.add("ex:creationDate", new GregorianCalendar(1885, Calendar.APRIL, 1).getTime())
					
					// the painting shows 5 people
					.add("ex:peopleDepicted", 5)
				.build();

		// To see what's in our model, let's just print stuff to the screen
		for(Statement st: model) {
			// we want to see the object values of each property
			IRI property = st.getPredicate();
			Value value = st.getObject();
			if (value instanceof Literal) {
				Literal literal = (Literal)value;
				output += "datatype: " + literal.getDatatype() + "\n";
				
				// get the value of the literal directly as a Java primitive.
				if (property.getLocalName().equals("peopleDepicted")) {
					int peopleDepicted = literal.intValue();
					output += peopleDepicted + " people are depicted in this painting" + "\n";
				}
				else if (property.getLocalName().equals("creationDate")) {
					XMLGregorianCalendar calendar = literal.calendarValue();
					Date date = calendar.toGregorianCalendar().getTime();
					output += "The painting was created on " + date + "\n";
				}
				
				// you can also just get the lexical value (a string) without worrying about the datatype
				output += "Lexical value: '" + literal.getLabel() + "'" + "\n";
			}
		}
    return output;
	}

 public String example04_RDF4J() {
    String output = "";
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		// Create a new RDF model containing information about the painting "The Potato Eaters"
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:PotatoEaters")
					// this painting was created on April 1, 1885
					.add("ex:creationDate", vf.createLiteral("1885-04-01T00:00:00Z", XMLSchema.DATETIME))
					// You can also pass in a Java Date object directly: 
						//.add("ex:creationDate", new GregorianCalendar(1885, Calendar.APRIL, 1).getTime())
					
					// the painting shows 5 people
					.add("ex:peopleDepicted", 5)

					// In English, this painting is called "The Potato Eaters"
					.add(DC.TITLE, vf.createLiteral("The Potato Eaters", "en"))
					// In Dutch, it's called "De Aardappeleters"
					.add(DC.TITLE,  vf.createLiteral("De Aardappeleters", "nl"))

					.add(DC.TITLE, vf.createLiteral("Manger Pomme de terre", "fr"))
						
				.build();

		// To see what's in our model, let's just print stuff to the screen
		for(Statement st: model) {
			// we want to see the object values of each property
			IRI property = st.getPredicate();
			Value value = st.getObject();
			if (value instanceof Literal) {
				Literal literal = (Literal)value;
				output += "datatype: " + literal.getDatatype() + "\n";
				
				// get the value of the literal directly as a Java primitive.
				if (property.getLocalName().equals("peopleDepicted")) {
					int peopleDepicted = literal.intValue();
					output += peopleDepicted + " people are depicted in this painting" + "\n";
				}
				else if (property.getLocalName().equals("creationDate")) {
					XMLGregorianCalendar calendar = literal.calendarValue();
					Date date = calendar.toGregorianCalendar().getTime();
					output += "The painting was created on " + date + "\n";
				}

		    // we want to see the object values of each statement
				output += "language: " + literal.getLanguage().orElse("unknown");
				output += " title: " + literal.getLabel() + "\n";
			}
		}
    return output;
	}

 public String example05_RDF4J() {
    String output = "";
		// To create a blank node for the address, we need a ValueFactory
		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode address = vf.createBNode();

		// First we do the same thing we did in example 02: create a new ModelBuilder, and add
		// two statements about Picasso.
		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:Picasso")
					.add(RDF.TYPE, "ex:Artist")
					.add(FOAF.FIRST_NAME, "Pablo")
				// this is where it becomes new: we add the address by linking the blank node
				// to picasso via the `ex:homeAddress` property, and then adding facts _about_ the address
					.add("ex:homeAddress", address) // link the blank node
				.subject(address)			// switch the subject
					.add("ex:street", "31 Art Gallery")
					.add("ex:city", "Madrid")
					.add("ex:country", "Spain");

		Model model = builder.build();

		// To see what's in our model, let's just print it to the screen
		for(Statement st: model) {
			output += st;
		}
	
    return output;
	}

public String example06_RDF4J() {
    String output;
		// To create a blank node for the address, we need a ValueFactory
		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode address = vf.createBNode();

		// First we do the same thing we did in example 02: create a new ModelBuilder, and add
		// two statements about Picasso.
		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:Picasso")
					.add(RDF.TYPE, "ex:Artist")
					.add(FOAF.FIRST_NAME, "Pablo")
				// this is where it becomes new: we add the address by linking the blank node
				// to picasso via the `ex:homeAddress` property, and then adding facts _about_ the address
					.add("ex:homeAddress", address) // link the blank node
				.subject(address)			// switch the subject
					.add("ex:street", "31 Art Gallery")
					.add("ex:city", "Madrid")
					.add("ex:country", "Spain");

		Model model = builder.build();


		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// Instead of simply printing the statements to the screen, we use a Rio writer to
		// write the model in RDF/XML syntax:
		Rio.write(model, os, RDFFormat.RDFXML);

		// Note that instead of writing to the screen using `System.out` you could also provide
		// a java.io.FileOutputStream or a java.io.FileWriter to save the model to a file
		// or a byte array as implemented here

		try {
			//All your IO Operations
			output = new String(os.toByteArray(),"UTF-8");
		} catch(IOException ioe) {
			//Handle exception here, most of the time you will just log it.
			output = "IO exception :(";
		}

    return output;
	}

public String example07_RDF4J() {
    String output;
		// To create a blank node for the address, we need a ValueFactory
		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode address = vf.createBNode();

		// First we do the same thing we did in example 02: create a new ModelBuilder, and add
		// two statements about Picasso.
		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace("ex", "http://example.org/")
				.subject("ex:Picasso")
					.add(RDF.TYPE, "ex:Artist")
					.add(FOAF.FIRST_NAME, "Pablo")
				// this is where it becomes new: we add the address by linking the blank node
				// to picasso via the `ex:homeAddress` property, and then adding facts _about_ the address
					.add("ex:homeAddress", address) // link the blank node
				.subject(address)			// switch the subject
					.add("ex:street", "31 Art Gallery")
					.add("ex:city", "Madrid")
					.add("ex:country", "Spain");

		Model model = builder.build();


		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// Instead of simply printing the statements to the screen, we use a Rio writer to
		// write the model in RDF/XML syntax:
		Rio.write(model, os, RDFFormat.TURTLE);

		// Note that instead of writing to the screen using `System.out` you could also provide
		// a java.io.FileOutputStream or a java.io.FileWriter to save the model to a file
		// or a byte array as implemented here

		try {
			//All your IO Operations
			output = new String(os.toByteArray(),"UTF-8");
		} catch(IOException ioe) {
			//Handle exception here, most of the time you will just log it.
			output = "IO exception :(";
		}

    return output;
	}

public String example08_RDF4J() {
    String output = "";
		Model model = null;

		String filename = "example-data-artists.ttl";

		try {
			//All your IO Operations

			// read the file 'example-data-artists.ttl' as an InputStream.
			InputStream input = Greeter.class.getResourceAsStream("/" + filename);

			// Rio also accepts a java.io.Reader as input for the parser.
			model = Rio.parse(input, "", RDFFormat.TURTLE);

		} catch(IOException ioe) {
			//Handle exception here, most of the time you will just log it.
			output = "IO exception :(";
		}

		if (model != null)
		{
			// To check that we have correctly read the file, let's print out the model to the screen again
			// To see what's in our model, let's just print it to the screen
			for(Statement st: model) {
				output += st;
			}
		}

		return output;
	}

	public String example09_RDF4J() {
		String output = "";
		Model model = null;

		String filename = "example-data-artists.ttl";

		try {
			//All your IO Operations

			// read the file 'example-data-artists.ttl' as an InputStream.
			InputStream input = Greeter.class.getResourceAsStream("/" + filename);

			// Rio also accepts a java.io.Reader as input for the parser.
			model = Rio.parse(input, "", RDFFormat.TURTLE);

		} catch(IOException ioe) {
			//Handle exception here, most of the time you will just log it.
			output = "IO exception :(";
		}

		if (model != null)
		{
			ValueFactory vf = SimpleValueFactory.getInstance();

			// We want to find all information about the artist `ex:VanGogh`.
			IRI vanGogh = vf.createIRI("http://example.org/VanGogh");

			// By filtering on a specific subject we zoom in on the data that is about that subject.
			// The filter method takes a subject, predicate, object (and optionally a named graph/context)
			// argument. The more arguments we set to a value, the more specific the filter becomes.
			Model aboutVanGogh = model.filter(vanGogh, null, null);

			// Iterate over the statements that are about Van Gogh
			for (Statement st: aboutVanGogh) {
				// the subject will always be `ex:VanGogh`, an IRI, so we can safely cast it
				IRI subject = (IRI)st.getSubject();
				// the property predicate can be anything, but it's always an IRI
				IRI predicate = st.getPredicate();

				// the property value could be an IRI, a BNode, or a Literal. In RDF4J, Value is
				// is the supertype of all possible kinds of RDF values.
				Value object = st.getObject();

				// let's print out the statement in a nice way. We ignore the namespaces and only print the
				// local name of each IRI
				output += subject.getLocalName() + " " + predicate.getLocalName() + " " + "\n";
				if (object instanceof Literal) {
					// it's a literal value. Let's print it out nicely, in quotes, and without any ugly
					// datatype stuff
					output += "\"" + ((Literal)object).getLabel() + "\"" + "\n";
				}
				else if (object instanceof  IRI) {
					// it's an IRI. Just print out the local part (without the namespace)
					output += ((IRI)object).getLocalName() + "\n";
				}
				else {
					// it's a blank node. Just print it out as-is.
					output += object + "\n";
				}
			}


		}

		return output;
	}



}