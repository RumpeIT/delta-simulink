Nutzung des Delta-Simulink Tools
--------------------------------------------------------------------------------
1. Navigieren zum Ordner, in dem das Tool-Jar liegt
2. Das Tool erwartet vier Argumente:
    2.1 Simulink Kernmodell Ordner
    2.2 Simulink Deltamodell Ordner
    2.3 Produktkonfigurationsdatei
    2.4 Ausgabeordner
3. Beispiel:
    java -jar de.mc.simulink_XYZ.jar sl/core sl/deltas sl/prod/myProd.delta out
    3.0 Nutzt Delta-Simulink Tool Version XYZ
    3.1 Exportiert Simulink Kernmodelle aus dem Ordner sl/core
    3.2 Exportiert Simulink Deltamodelle aus dem Ordner sl/deltas
    3.3 Generiert das in sl/prod/myProd.delta beschriebene Produkt
    3.4 Erzeugt die Produkt-Simulinkmodelle im Ordner out

Der unter 3 beschriebene Aufruf startet die main() Methode der Klasse 
mc.deltasimulink.DeltaSimulinkTool. Diese Methode erzeugt eine neue Instanz des
DeltaSimulinkTools und leitet die übergeben Argumtente an den Konstruktor der 
Klasse weiter. Soll das Tool also im Kontext einer anderen Javaimplementierung
wiederverwendet werden, erwartet der Konstruktur die selben Parameter (vgl. 2.).

java -jar de.mc.deltasimulink_0.2.0.jar 
          casestudy/core/ 
          casestudy/deltas/ 
          casestudy/products/03.ABS_TC_ESC/ABS_TC_ESC.delta 
          generated