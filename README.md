ds-bean
=======
The Doctusoft Bean framework provides a basis for the techniques we liked to use at [Doctusoft Ltd](http://www.doctusoft.com), related to Java Beans (entities, DTOs or whatever). It's best used with lombok-ds, a fork of [lombok-pg](https://github.com/peichhorn/lombok-pg) to help us generate some boilerplate code.

##Main features
- Bean Property Literals
- Bean Method Literals
- Value Binding support

## Property Literals
Property literals can help you create frameworks that can reflect bean properties in a type-safe way. The Hibernate Criteria API is a good example for that need, but our Value Binding support also relies on this feature.

### Usage
You can declare attribute literals with lombok-ds:
```java
public class SomeClass {
    @Attribute @Getter @Setter
    private String someProperty;
}
```
The code above will create an
```java
public static final Attribute&lt;SomeClass, String&gt; _someProperty;
```
on the class.
The `Attribute` interface provides the following methods:
```java
public interface Attribute&lt;E, T&gt; {
	T getValue( E instance );
	void setValue( E instance, T value );
	Class<E> getParent();
	Class<T> getType();
	String getName();
}
```
Lombok-ds will implement this interface inline for each attribute. This works the fastest during runtime, and it also works with GWT.

If you can't use lombok-ds for some reason, you can manually create these literals using the `AttributeImpl` class in `ds-bean-reflected` - this relies on reflection, so it's a bit slower and works only in a JVM, not in GWT.

##Value Binding
Value binding relies on attribute literals described above. It's under the `ds-bean-binding` project. The [`TestValueBindingBuilder`](https://github.com/Doctusoft/ds-bean/blob/master/ds-bean-binding/src/main/test/com/doctusoft/common/core/bean/binding/TestValueBindingBuilder.java) provides simple examples of creating and using value bindings.

###Basic usage examples
```java
ValueBindingBuilder.on(model)
    .get(MyModel._task)
    .get(MyTask._assignee)
    .get(MyAssignee._name);
```
Conversion:
```java
ValueBindingBuilder.on(model)
    .get(MyModel._task)
    .get(MyTask._dueDate)
    .convert(new BasicDateConverter())
```

You can easily wrap and use the generic ValueBinding interface to provide bindings in the environment you are using (we are using it with JSF, Vaadin and GWT applications). We will soon release some example wrappers.

Release notes
-------------
- 0.1.3
  - Projects restructured
  - Value Binding added
- 0.1.2
  - Added `ClassMethodLiteral` and `ObjectMethodLiteral` that will be generated with `@MethodRef` in lombok-ds-0.1.2.
  - Deprecated `Attributes` and `AttributeImpl` classes that rely on reflection and apache common beanutils. Reflection based implementation runs slower and doesn't work with GWT. In the other hand, the new anonymous class based implementation generates lots of code, but that shouldn't be a problem.