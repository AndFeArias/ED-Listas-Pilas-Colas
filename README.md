# Implementación y Análisis de Complejidad de Listas, Pilas y Colas en Java

* **Autor:** Andrés Felipe Arias ([@AndFeArias](https://github.com/AndFeArias))
* **Asignatura:** Estructuras de Datos (2026-2)
* **Profesor:** David Herrera

Este repositorio contiene la implementación desde cero en Java de las estructuras de datos **Listas Enlazadas** (en sus 4 distintas variantes), **Pilas (`MyStack`)** y **Colas (`MyQueue`)** basadas en arreglos dinámicos/circulares redimensionables. Además, incluye un marco de pruebas experimentales para medir el tiempo de ejecución de las operaciones y realizar un análisis comparativo de complejidad teórica ($Big-O$) vs. empírica con tamaños de entrada desde $10^1$ hasta $10^5$.

---

## Tabla de Contenidos
- [Instrucciones de Compilación y Ejecución](#-instrucciones-de-compilación-y-ejecución)
- [Objetivos del Taller](#-objetivos-del-taller)
- [Estructura del Repositorio](#-estructura-del-repositorio)
- [Estructuras e Interfaces Implementadas](#-estructuras-e-interfaces-implementadas)
  - [1. Listas Enlazadas (`List`)](#1-listas-enlazadas-list)
  - [2. Pilas (`MyStack<T>`)](#2-pilas-mystackt)
  - [3. Colas (`MyQueue<T>`)](#3-colas-myqueuet)
- [Análisis de Complejidad Teórica ($Big-O$)](#-análisis-de-complejidad-teórica-big-o)
- [Medición de Tiempos y Pruebas Empíricas](#-medición-de-tiempos-y-pruebas-empíricas)
- [Conclusiones](#-conclusiones-y-lecciones-aprendidas)
- [Créditos](#-créditos)

---
# Instrucciones de Compilación y Ejecución

### Prerrequisitos
* Java JDK 17 o superior.
* IDE opcional (IntelliJ IDEA, Eclipse, VS Code) o consola de comandos.

### Pasos para Ejecutar

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/AndFeArias/ED-Listas-Pilas-Colas.git
   cd ED-Listas-Pilas-Colas
   ```

2. **Compilar el proyecto desde la carpeta `src`:**
   ```bash
   javac -d bin src/**/*.java src/*.java
   ```

3. **Ejecutar la suite de pruebas y benchmark:**
   ```bash
   java -cp bin Main
   ```
## Objetivos del Taller

1. Implementar la estructura de datos `List` en Java mediante cuatro variaciones de listas enlazadas (con/sin puntero a cola `tail` y sencillas/doblemente enlazadas).
2. Definir e implementar las interfaces genéricas `MyStack<T>` y `MyQueue<T>` utilizando arreglos dinámicos/circulares con estrategia de redimensionamiento.
3. Medir experimentalmente los tiempos de ejecución en **microsegundos ($\mu s$) / nanosegundos ($ns$)** ante diferentes tamaños de entrada aleatoria ($N \in \{10^1, 10^2, 10^3, 10^4, 10^5\}$).
4. Comparar el rendimiento práctico frente al análisis teórico asintótico en notación $Big-O$.

---

## Estructura del Repositorio
```text
ED-Listas-Pilas-Colas/
├── src/
│   ├── interfaces/
│   │   ├── MyList.java                                   # Interfaz MyList<T>
│   │   ├── MyStack.java                                  # Interfaz MyStack<T>
│   │   └── MyQueue.java                                  # Interfaz MyQueue<T>
│   ├── lists/  
│   │   ├── SinglyLinkedList.java                         # Lista Sencilla Sin Cola
│   │   ├── SinglyLinkedListWithTail.java                 # Lista Sencilla Con Cola
│   │   ├── DoublyLinkedList.java                         # Lista Doble Sin Cola
│   │   └── DoublyLinkedListWithTail.java                 # Lista Doble Con Cola
│   ├── stackqueue/
│   │   ├── ArrayStack.java                               # Implementación del Stack
│   │   └── DynamicArrayQueue.java                        # Implementación con Arreglo Circular Dinamico
│   └── main.java                                         # Punto de entrada principal para ejecutar pruebas
├── docs/
│   ├── Stack-Queue-Java-ED-# Andrés Felipe Arias.pdf     # Informe completo con gráficos y análisis
│   └── resultados_benchmark.csv                          # Datos crudos obtenidos en formato .cvs
├── README.md
└── .gitignore
```
# Estructuras e Interfaces Implementadas

## 1. Listas Enlazadas (`List`)

Se implementaron 4 variantes para evaluar el impacto de almacenar un puntero `tail` (cola) y enlaces dobles (`prev` / `next`):

* **`SinglyLinkedList`**: Lista simplemente enlazada sin referencia al último nodo.
* **`SinglyLinkedListWithTail`**: Lista simplemente enlazada con referencia `head` y `tail`.
* **`DoublyLinkedList`**: Lista doblemente enlazada sin referencia al último nodo.
* **`DoublyLinkedListWithTail`**: Lista doblemente enlazada con referencias `head` y `tail`.

### Métodos mínimos soportados:
* `pushFront(T data)` / `pushBack(T data)`
* `popFront()` / `popBack()`
* `find(T data)` / `erase(T data)`
* `addBefore(Node target, T data)` / `addAfter(Node target, T data)`
* **Auxiliares:** `isEmpty()`, `topBack()`, `topFront()`

---

## 2. Pilas (`MyStack<T>`)

Interfaz genérica basada en la filosofía **LIFO** (*Last In, First Out*).

```java
public interface MyStack<T> {
    void push(T x);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}
```

**Implementación:** Se utilizó un arreglo dinámico (`DynamicArrayStack`). Cuando el contenedor se llena, su capacidad se duplica ($2 \times \text{capacity}$), garantizando un tiempo amortizado $O(1)$ por inserción.

---

## 3. Colas (`MyQueue<T>`)

Interfaz genérica basada en la filosofía **FIFO** (*First In, First Out*).

```java
public interface MyQueue<T> {
    void enqueue(T x);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
    boolean delete(T n);
}
```

**Implementación:** Se utilizó un arreglo circular (`CircularArrayQueue`) con índices `front` y `rear` y redimensionamiento automático al 100% de capacidad, logrando que `enqueue` y `dequeue` sean operaciones $O(1)$.

---

# Análisis de Complejidad Teórica (Big-O)

### Comparativa de Métodos en `List`

| Método | Singly No Tail | Singly Tail | Doubly No Tail | Doubly Tail |
| :--- | :---: | :---: | :---: | :---: |
| `pushFront(x)` | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| `pushBack(x)` | $O(n)$ | $O(1)$ | $O(n)$ | $O(1)$ |
| `popFront()` | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| `popBack()` | $O(n)$ | $O(n)$ | $O(n)$ | $O(1)$ |
| `find(x)` | $O(n)$ | $O(n)$ | $O(n)$ | $O(n)$ |
| `erase(x)` | $O(n)$ | $O(n)$ | $O(n)$ | $O(n)$ |
| `addBefore(node, x)` | $O(n)$ | $O(n)$ | $O(1)$ | $O(1)$ |
| `addAfter(node, x)` | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |

### Comparativa `MyStack` vs `MyQueue` (Arreglo Dinámico / Circular)

| Operación | MyStack (Dynamic Array) | MyQueue (Circular Array) |
| :--- | :--- | :--- |
| **Insertar** (`push` / `enqueue`) | $O(1)$ amortizado / $O(n)$ realloc | $O(1)$ amortizado / $O(n)$ realloc |
| **Eliminar** (`pop` / `dequeue`) | $O(1)$ | $O(1)$ |
| **Consultar** (`peek` / `front`) | $O(1)$ | $O(1)$ |
| **Buscar / Eliminar Valor** (`delete(n)`) | $O(n)$ | $O(n)$ |

---

# Medición de Tiempos y Pruebas Empíricas

* **Unidad de Medición:** Se utilizó `System.nanoTime()` convertida a microsegundos ($\mu\text{s}$) para mantener precisión sin perder la escala intuitiva frente a valores de gran magnitud ($10^5$).
* **Aislamiento de Métricas:** Las mediciones se realizaron estrictamente fuera de los bloques de I/O, generación aleatoria y graficación para evitar sesgos en el tiempo de ejecución.

### Resumen de Resultados Experimétricos (Ejemplo $N = 10^1$ a $10^5$)
1. **`popBack` en Singly Linked List:** Muestra un crecimiento claramente lineal $O(n)$ debido a la necesidad de recorrer toda la lista hasta el penúltimo nodo.
2. **`DoublyLinkedListTail`:** Logra mantener la operación `popBack` en tiempo constante $O(1)$.
3. **Arreglo Dinámico vs Lista Enlazada:** Los arreglos presentan mayor eficiencia en localización de memoria (*cache locality*) y menor sobrecosto por objeto/puntero en comparación con nodos enlazados en el Heap.


---

# Conclusiones

* **Beneficios del Puntero `tail`:** La inclusión de una referencia al último elemento convierte la operación `pushBack` de $O(n)$ a $O(1)$ en listas sencillas, y tanto `pushBack` como `popBack` en $O(1)$ cuando se combina con enlaces dobles.
* **Listas Enlazadas vs. Arreglos Dinámicos:**
  * **Listas Enlazadas:** Ideales cuando se requiere tamaño dinámico no predecible e inserciones/eliminaciones constantes $O(1)$ en la cabeza o posiciones intermedias teniendo el nodo. Tienen un mayor sobrecosto de memoria por punteros (`next`/`prev`).
  * **Arreglos Dinámicos / Circulares:** Ofrecen excelente uso de caché y bajo consumo de memoria por elemento. Aunque el redimensionamiento requiere $O(n)$, el costo amortizado sigue siendo $O(1)$.
* **Casos de Uso en Aplicaciones Reales:**
  * **Pilas (LIFO):** Gestión de llamadas a funciones (*call stack*), evaluación de expresiones matemáticas y funcionalidad Deshacer/Rehacer (Ctrl+Z) en editores de texto.
  * **Colas (FIFO):** Buffers de impresión, colas de procesamiento de tareas (*job scheduling*), algoritmos de búsqueda en anchura (BFS) y atención de solicitudes en servidores web.

---
* **Autor:** Andrés Felipe Arias ([@AndFeArias](https://github.com/AndFeArias))
