# Sortieren
****
Es wurden folgende Sortierungs Algorithmen implementiert: __MergeSort, QuickSort, QuickSort- Horaes, QuickSort - Pivot in the middle, QuickSort - Pivot is Random, QuickSort - without Partitioning__; 


### TestSorting.java

1. Testet Arrays n = __(10, 15, 20, 25, 50, 100, 200, 500, 1000, 2000, 3000, 10000, 20000, 40000, 90000, 1000000)__
2. erstellt Random Array(n)
3. kopiert Random Array() in Temporär Array __(temp0, temp1, temp2, temp3, temp4, temp5)__
4. sortiert Arrays mit QuickSort, MergeSort, ...;
5. misst die Zeit der Dauer des sortierens in __ns__
6. __assertTrue(isSorted(temp0))__ prüft ob die Arrays sortiert wurden
7. Datenmodelle werden aus den Sortierungs Algorithmen und der gemossenen Zeit erstellt
8. __List<Result>__ sortiert die Datenmodelle nach der Zeit
9. __assertTrue(result.getSteps() <= result.getWorstCase() && result.getSteps() >= result.getBestCase())__ überprüft ob die gemossene Zeit innerhalb bestCase und worstCase liegen
10. results.sort(Comparator.comparingLong(Result::__getTime__)) umstellen zu
* (::__getSteps__) nach __Steps__ sortieren
* (::__getBestCase__) nach __BestCase__ sortieren
* (::__getAvgCase__) nach __AverageCase__ sortieren
* (::__getWorstCase__) nach __WorstCase__ sortieren
11. Ausgabe der Ergebnisse im Terminal

``` 
N = 10
Time taken: 3900 ns, steps = 44 expected(best): 29 expected(avg): 40 expected(worst): 81, Algorithm: MiddlePivot
Time taken: 4700 ns, steps = 84 expected(best): 29 expected(avg): 40 expected(worst): 81, Algorithm: Horaes
Time taken: 5300 ns, steps = 29 expected(best): 29 expected(avg): 40 expected(worst): 81, Algorithm: QuickSort
Time taken: 6900 ns, steps = 34 expected(best): 29 expected(avg): 29 expected(worst): 29, Algorithm: MergeSort
Time taken: 8400 ns, steps = 73 expected(best): 29 expected(avg): 40 expected(worst): 81, Algorithm: WithoutPartitiong
Time taken: 12300 ns, steps = 48 expected(best): 29 expected(avg): 40 expected(worst): 81, Algorithm: RandomPivot
```
  
  
