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
}