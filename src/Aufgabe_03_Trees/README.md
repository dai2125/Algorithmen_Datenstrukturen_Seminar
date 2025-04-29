# Binärer Suchbaum
****
Generic Binary Search Tree implementier nach Angabe.

## Features

- Generischer BST (`T extends Comparable<T>`)
- Konsolen Eingabe
- Unterstützte Operationen:
    - **add(`<value>`)**
    - **remove(`<value>`)**
    - **update(`<old> <new>`)**
    - **successor(`<value>`)**
    - **predecessor(`<value>`)**
    - **traversal(TreeTraversalOrder `<PRE_ORDER>` || `<IN_ORDER>` || `<POST_ORDER>` || `<LEVEL_ORDER>`)**
    - **print()**

## Kommandos
```
{ -successor -value } [PRINTS SUCCESSOR]	
{ -predecessor -value } [PRINTS PREDECESSOR]
{ -add -value } [ADDS VALUE]
{ -print } [PRINTS BINARY SEARCH TREE]		
{ -remove -value } [REMOVES VALUE]				
{ -update -old value -new value } [UPDATES VALUE] 
{ -inorder } [PRINTS IN ORDER TRAVERSAL]	
{ -preorder } [PRINTS PRE ORDER TRAVERSAL]		
{ -postorder } [PRINTS POST ORDER TRAVERSAL]	
{ -level } [PRINTS LEVEL ORDER TRAVERASAL]
{ -exit } [SHUT DOWN PROGRAM]
```

## Tests
- TestSuccessor()
- TestPredecessor()
- TestMinimum()
- TestMaximum()
- TestTraversal()
- TestInorderTraversal()
- TestPreorderTraversal()
- TestPostorderTraversal()
- TestRemove()
- TestUpdateNode()


# AVL
***
AVL implementiert nach Angabe.

## Features

- Generischer AVL (`T extends Comparable<T>`)
- Konsolen Eingabe
- Unterstützte Operationen:
    - **add(`<value>`)**
    - **remove(`<value>`)**
    - **update(`<old> <new>`)**
    - **successor(`<value>`)**
    - **predecessor(`<value>`)**
    - **print()**

## Kommandos
```
{ -successor -value } [PRINTS SUCCESSOR]	
{ -predecessor -value } [PRINTS PREDECESSOR]
{ -add -value } [ADDS VALUE]
{ -print } [PRINTS AVL]		
{ -remove -value } [REMOVES VALUE]				
{ -update -old value -new value } [UPDATES VALUE] 
{ -exit } [SHUT DOWN PROGRAM]
```