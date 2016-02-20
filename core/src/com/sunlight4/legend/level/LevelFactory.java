package com.sunlight4.legend.level;
	import org.python.core.PyObject;
	import org.python.core.PyString;
	import org.python.util.PythonInterpreter;

	public class LevelFactory {

	    private PyObject levelClass;

	    /**
	     * Create a new PythonInterpreter object, then use it to
	     * execute some python code. In this case, we want to
	     * import the python module that we will coerce.
	     *
	     * Once the module is imported than we obtain a reference to
	     * it and assign the reference to a Java variable
	     */

	    public LevelFactory(String moduleName) {
	        PythonInterpreter interpreter = new PythonInterpreter();
	        interpreter.exec("from " + moduleName + " import Level");
	        levelClass = interpreter.get("Level");
	   
	    }

	    /**
	     * The create method is responsible for performing the actual
	     * coercion of the referenced python module into Java bytecode
	     */

	    public Level create () {

	        PyObject levelObject = levelClass.__call__();
	        return (Level)levelObject.__tojava__(Level.class);
	    }

	}
