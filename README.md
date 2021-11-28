# JUMP2DIGITAL-PaymentAnalytics

## Estructura

Estamos frente a una arquitectura de CEBOLLA, formada por capas 
donde cada una tiene una finalidad (de ahí que haya inyección de dependencias).
Esta arquitectura se presenta de la siguiente manera:

<img width="591" alt="Estructura back" src="https://user-images.githubusercontent.com/79535775/143778804-21c4eecc-2476-403a-9bf2-5f9bb9cfcffa.png">

Como podemos ver en el esquema, ya resalta la importancia de la validación de los datos de entrada frente a los de salida, que no irán validados. Esto es así ya que la información sensible y donde puede haber un gran número de errores es al introducir datos. También 
hemos de tener en cuenta que nuestro dominio jamás deberá quedar expuesto, por ello trabajamos con DTO (Data Transfer Objects), que no son más que clases que contienen atributos y métodos análogos a los objetos de nuestro dominio pero que lo protegen del resto de capas.
A continuación, se explicará una a una las capas del proyecto.

## Dominio
Dentro del dominio encontramos las clases que formarán parte de nuestro sistema de pago: products y tickets. Ambas estarán validadas para guardar informacion correctamente en la base de datos cuando los atributos son requeridos o no pueden ser nulos, así como si algún valor numérico debe ser mayor que 0. Estas clases heredan de EntityBase que se encuentra en el core ya que ambas comparten el atributo id (UUID). Se realizará a su vez un override al método toString ede tal forma que devuelva una cadena con los atributos de la clase y sus valores correspondientes.
En el dominio también tendremos la proyección del objeto en cuestión que nos será de utilidad en la capa de Infraestructura, así como los repositorios donde se encuentran los métodos que nos servirán para añadir, encontrar, borrar, actualizar y hallar todos los objetos de ese tipo desde otras capas. Estos repositorios no son más que interfaces que se inyectarán a capas superiores donde serán implementadas y está segregado entre escritura y lectura, donde la interfaz de escritura hereda de las interfaces FindById<Product/Ticket, UUID> y ExistsByField.

## Infraestructura
En la capa de infraestructura encontraremos una interfaz que extiende de JPARepository de Spring que admite dos genéricos <T, ID> donde nosotros pasaremos el Objeto en cuestión y el UUID. En esta interfaz encontramos las querys necesarias para realizar los métodos findByField, findByCriteria, exists… que no son más que llamadas a la base de datos para encontrar la información de la que precisamos. Estas querys se 
harán gracias a la notación @Query y se pondrá en el método en cuestión que lleva el 
nombre de la declaración del mismo.

También podemos encontrar la clase del repositorio que implementa la interfaz declarada en el dominio. En ella tendremos la anotación @Repository, que hará que sea encontrada como si fuera un Bean de Spring. El constructor de la misma irá anotado con @Autowired para que se inyecten las dependencias necesarias para la instanciación de esa clase en cuestión. En esta clase, se encuentran todos los métodos con los que podremos jugar en nuestra base de datos, esto es: añadir, eliminar, actualizar, obtener y obtener todos los objetos. En el caso de products también estará implementado el método exists que en capas posteriores nos servirá para comprobar si el objeto que buscamos existe en la base de datos.

## Aplicación
La capa de aplicación es la encargada de coger un DTO de entrada y convertirlo a un 
objeto de mi dominio y viceversa. Esto es, esta capa se encarga de realizar el mapeo 
gracias a la inyección de un bean de modelMapper (dependencia que habrá que incluir 
en el proyecto). Este modelMapper se encarga de transformar el DTO de entrada en un 
objeto o al revés mediante la búsqueda de métodos con la palabra set o get mediante 
el uso de reflexión. Es por ello que nuestras clases de dominio y nuestros DTO tienen 
atributos privados y están declaradas con @Setter y @Getter de Lombok. 
En esta capa también se realizan las validaciones. Por ejemplo, hemos de comprobar si 
al añadir un objeto, este existe previamente en nuestra base de datos, para que en caso 
afirmativo nos lance un error inidicando que no puede haber dos objetos con el mismo 
parámetro “name” (por ejemplo). A su vez, se puede validar que los parámetros 
introducidos en formato json son los correctos. Todo ello a través de dos métodos que 
se explicarán en EntityBase y que están sobreescritos (validate).
En esta capa deberían realizarse también las transacciones, no obstante, en esta 
aplicación no es necesario debido a la naturaleza de los objetos y cómo se guardan en 
la base de datos. 
Por último, encontramos que debemos realizar el log. En mi caso, en esta capa 
solamente realizamos logs cuando se ha realizado una acción de forma satisfactoria, ya 
que el control de logs de los errores se recogerá en el core en la parte de excepciones. 
El log, al igual que el modelMapper, se inyectará a esta capa gracias a la creación de un 
Bean en el core del tipo singleton (para que solamente se instancie una vez) e 
inyectándoselo en el constructor de la clase principal de esta capa. 
Para no incumplir con las buenas prácticas de SOLID esta clase mencionada es la 
implementación de una interfaz. Esto es así para trabajar con abstracciones y no con 
implementaciones en la capa de Controlador. 
La clase que está implementada en aplicación viene anotada con @Service, anotación 
que permitirá que Spring reconozca la clase como servicio al escanear los componentes 
de la aplicación.
Como hemos podido ver, se realiza un control exhaustivo de los datos de entrada, pero 
a la hora de devolver la información se emplean DTO que no están validados ya que lo 
que hacemos básicamente es escupir información.

## Controlador
El controlador es la última capa de nuestra aplicación, debe ser una capa muy liviana 
debido a la gran variabilidad de la tecnología en Front-end. Se ha tratado de realizar el 
controlador con el menor número de líneas posible para tener una mayor flexibilidad.
Esta capa es la encargada de lidiar con el protocolo HTTP. En ella tiene lugar la reflexión, 
para permitirnos pasar de objeto a cadena y viceversa. 
A esta capa se le inyecta la interfaz de la capa de aplicación explicada anteriormente y 
se encuentra anotada con @RestController de tal forma que se crea un Servicio REST en 
Spring. A su vez también irá anotada con @RequestMapping, anotación que se utiliza 
para asignar solicitudes web con los métodos de Spring Controller. Al igual que en otras 
capas, emplearemos @Autowired para el constructor de la clase de controlador. 
Los métodos que componen esta clase irán anotados con @PostMapping, 
@PutMapping, @GetMapping y @DeleteMapping correspondiéndose con los distintos 
métodos HTTP que encontramos en el protocolo y servirán para añadir, modificar, 
obtener y eliminar, respectivamente. Los métodos irán con anotaciones de variables que 
son necesarias para realizar las llamadas y @Valid que sirve para validar lo que 
previamente se anotó como @Validated. Estos métodos devolverán un response 
indicando que se ha realizado correctamente la petición gracias al estado y un body con 
información del objeto obtenido o añadido en los correspondientes casos.
A continuación explicaré la carpeta core donde se encuentra la columna vertebral de 
nuestra aplicación y donde se realiza tanto el manejo de excepciones, como la creación 
de los singleton para inyectar las dependencias, interfaces funcionales y la clase 
EntityBase.

## CORE

### EntityBase
Es la clase abstracta de la que heredan todas las demás, ya que todas ellas comparten 
que tendrán un atributo id del tipo UUID. A su vez, como es necesario validar todos los 
objetos, aquí encontramos 2 métodos validate que están sobreescritos, uno con 
parámetros y otro sin parámetros. Se encargaran de realizar la validación en caso de que 
encontremos algún error ya que uno de los métodos tiene una lista “Violations” que 
estará vacía en caso de que la validación sea correcta al finalizarse o bien enviará una 
excepción del tipo BadRequestException para indicar que la información que nos llega 
no es correcta. En el otro método de validación lo que realizamos es la comprobación 
de que no exista un campo duplicado (campo de interés, por ejemplo, name) y en caso 
de que lo esté nos lanza una excepción del tipo BadRequestException indicando “Value 
(valor) for key (nombre del campo) is duplicated. Aquí se emplea también la interfaz 
funcional ExistByField que se encuentra en el core.
A su vez se realizará un override a los métodos equals y hashcode para que a la hora de 
comprobar si un objeto es igual a otro me lo compare según el id del tipo UUID y no 
según el nombre del objeto.

### ExistsByField FindById
En el core podemos encontrar una carpeta functionalinterfaces donde se encuentran 
estas dos interfaces. 
FindById se encagará de buscar por ID y es una interfaz a la que se le pasarán dos 
genéricos <T, ID> donde posteriormente cuando en la infraestructura heredemos de 
esta interfaz pasaremos en T el objeto que queremos y en ID un objeto del tipo UUID y 
que devuelve un Optional<T> al pasarle el id.
En cuanto a ExistByField no es más que otra interfaz que llama al método exists y 
pasándole un campo que nos interesa, que bien puede ser el nombre, o el email, etc y 
nos devuelve un boolean indicando si existe o no. 
Estas dos interfaces, al igual que todas las funcionales solamente cuentan con un 
método y en la llamada a las mismas irá la implementación cuando corresponda.

### ApplicationBase
Application base es una clase abstracta que toma dos genéricos <T, ID> y que tiene un 
atributo del tipo FindById <T,ID>. Su constructor implementa la interfaz funcional 
FindById buscando por id el objeto que le pasamos y lanzando una excepción del tipo 
NotFoundException en caso de que no se haya podido encontrar. Como podemos ver 
en esta clase, se muestra la gran potencia que nos proporciona el uso de las interfaces 
funcionales y gracias a esta función hemos podido encapsular perfectamente el método 
findById para usarlo en la capa de aplicación sin tener que usar un try catch nada más 
que en un sitio.
En esta clase también encontramos el método serializeObject que toma como 
argumentos la entiedad y un mensaje tipo String y devuelve una cadena que indica que 
un método se ha realizado correctamente.
Siguiendo con los principios de buenas prácticas de Clean Code, el nombre de esta clase 
refleja perfectamente su utilidad, ya que nos sirve para no poner try/catch en la capa de 
aplicación en las llamadas a l método findById y para no particularizar en el método 
serializeObject en cada una de las capas de aplicación de los diferentes objetos. De esta 
clase heredará la implementación de la aplicación como bien se habrá podido deducir

### LoggerConfiguration y MapperConfiguration
Estas dos clases se encuentran dentro de la carpeta beansconfiguration y como su 
nombre indica son las responsables de crear un bean del tipo singleton del 
ModelMapper y el Logger que posteriormente podrá inyectarse en el constructor de la 
implementación de la capa de aplicación. Esto sirve para que solamente puedan ser 
instanciadas por ellos mismos una única vez a lo largo de la ejecución.

### Excepciones y ExceptionHandlers
El control de las excepciones viene implementado en el core, en las carpetas 
exceptionhandlers y exceptions. En exceptions se han creado todos los tipos de 
excepciones que queremos manejar, en este caso: BadRequestException, 
ForbiddenException, HttpException, InternalServerErrorException y 
NotFoundException. Todas ellas heredan de HttpException y en su constructor realizan 
una llamada a this con el mensaje de error que se desea transmitir. Este constructor va 
sobrecargado ya que también podemos pasar el mensaje de error como argumento e 
invocaríamos al constructor de HttpException (la superclase) pasandole el tipo de error 
y el mensaje. En algunas como por ejemplo, BadRequestException y ForbiddenException
que tienen un atributo map<String, String> que guardan un clave, valor con la excepción.
Todas estas excepciones tendrán asociadas un pequeño controlador para manejarlas en 
caso de que ocurran y todas ellas convergen a un mismo punto. Estos controladores 
vienen anotados con @ControllerAdvice, que nos permite manejar las excepciones de 
nuestra aplicación. Su principal función es la de no manejar las excepciones desde un 
contolador específico, sino que captura todas en un mismo punto. También sus métodos 
vienen anotados con @ExceptionHandler que nos permite manejar las excepciones a 
partir del métodod e la clase. En nuestro caso, con estos métodos devolvemos un 
ResponseEntity con el Http Status y el mensaje en el body como bien podría ser “Error
404”.





