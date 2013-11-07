ds-bean
=======

Release notes
-------------
- 0.1.2
  - Added `ClassMethodLiteral` and `ObjectMethodLiteral` that will be generated with `@MethodRef` in lombok-ds-0.1.2.
  - Deprecated `Attributes` and `AttributeImpl` classes that rely on reflection and apache common beanutils. Reflection based implementation runs slower and doesn't work with GWT. In the other hand, the new anonymous class based implementation generates lots of code, but that shouldn't be a problem.