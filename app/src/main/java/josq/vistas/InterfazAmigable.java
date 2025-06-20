package josq.vistas;

import josq.modelos.Tabla;
import josq.controles.CargarXML;
import josq.controles.Interacciones;
import josq.controles.Utilidades;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class InterfazAmigable extends Application
{
    static private String titulo = "JOSQ-DB";

    static Font font_mono;
    static String mono_name = "DMMono-Regular.ttf";
    static double mono_size = 13;

    static private Scene sc_inicio;
    static private Scene sc_open_table;
    static private Scene sc_show_diagrama;
    static private Scene sc_add_llave;
    static private Scene sc_add_valor;
    static private Scene sc_del_valor;
    static private Scene sc_add_tabla;
    static private Scene sc_add_colum;
    static private Scene sc_set_llave;
    static private Scene sc_show_valores;
    static private Scene sc_add_relac;
    static private Scene sc_del_llave;
    static private Scene sc_add_xml;

    @Override
    public void start(Stage stage1)
    {
        stage1.setTitle(titulo);

        initInicio();
        initAddDato();
        initAddTabla();
        initAddColumna();
        initOpenTabla();
        initAddLlave();
        initSetLlavePrimaria();
        initDeleteDato();
        initDelTupla();
        initAddRelacion();
        initCargarXML();

        stage1.setScene(sc_inicio);
        stage1.sizeToScene();

        stage1.show();
    }

    private void initInicio()
    {
        Label l_proyecto = new Label("Estructura de Datos, S1-2023");

        Button b11_ShowValores = new Button("DATOS EN TABLA");
        Button b12_ShowDiagrama = new Button("DIAGRAMA ER");
        Button b13_AddTabla = new Button("AGREGAR UNA TABLA");
        Button b14_AddColumna = new Button("AGREGAR UNA COLUMNA");
        Button b15_SetLlavePrimaria = new Button("FIJAR LLAVE PRIMARIA");
        Button b16_AddRelacion = new Button("AGREGAR RELACION");

        Button b21_AddLlave = new Button("INGRESAR UNA LLAVE");
        Button b22_AddValor = new Button("INGRESAR UN DATO");
        Button b23_DelValor = new Button("ELIMINAR UN DATO");
        Button b24_DelTupla = new Button("ELIMINAR UNA TUPLA");
        Button b25_CargarXML = new Button("CARGAR DATOS CON XML");
        Button bAyuda = new Button("AYUDA");

        b11_ShowValores.setMaxWidth(Double.MAX_VALUE);
        b12_ShowDiagrama.setMaxWidth(Double.MAX_VALUE);

        b21_AddLlave.setMaxWidth(Double.MAX_VALUE);
        b22_AddValor.setMaxWidth(Double.MAX_VALUE);
        b23_DelValor.setMaxWidth(Double.MAX_VALUE);
        b24_DelTupla.setMaxWidth(Double.MAX_VALUE);
        b25_CargarXML.setMaxWidth(Double.MAX_VALUE);

        b13_AddTabla.setMaxWidth(Double.MAX_VALUE);
        b14_AddColumna.setMaxWidth(Double.MAX_VALUE);
        b15_SetLlavePrimaria.setMaxWidth(Double.MAX_VALUE);
        b16_AddRelacion.setMaxWidth(Double.MAX_VALUE);
        bAyuda.setMaxWidth(Double.MAX_VALUE);

        b11_ShowValores.setOnAction((e) -> {abrirOpenTabla();});
        b12_ShowDiagrama.setOnAction((e) -> { try{abrirShowDiagrama();} catch(Exception x){} });

        b21_AddLlave.setOnAction((e) -> {abrirAddLlave();});
        b22_AddValor.setOnAction((e) -> {abrirAddDato();});
        b23_DelValor.setOnAction((e) -> {abrirDelDato();});
        b24_DelTupla.setOnAction((e) -> {abrirDelLlave();});
        b25_CargarXML.setOnAction((e) -> {abrirCargarXML();});

        b13_AddTabla.setOnAction((e) -> {abrirAddTabla();});
        b14_AddColumna.setOnAction((e) -> {abrirAddColumna();});
        b15_SetLlavePrimaria.setOnAction((e) -> {abrirSetLlavePrimaria();});
        b16_AddRelacion.setOnAction((e) -> {abrirAddRelacion();});
        bAyuda.setOnAction((e) -> {abrirAyuda();});

        int colum_width = 200;

        VBox v1 = new VBox();//v1.setMaxWidth(Double.MAX_VALUE);
        v1.setSpacing(6);
        v1.getChildren().addAll(b11_ShowValores,b12_ShowDiagrama,b13_AddTabla,b14_AddColumna,b15_SetLlavePrimaria,b16_AddRelacion);
        v1.setMinWidth(colum_width);
        
        VBox v2 = new VBox();//v2.setMaxWidth(Double.MAX_VALUE);
        v2.setSpacing(6);
        v2.getChildren().addAll(b21_AddLlave,b22_AddValor,b23_DelValor,b24_DelTupla,b25_CargarXML,bAyuda);
        v2.setMinWidth(colum_width);
        
        HBox h1 = new HBox();//h1.setMaxWidth(Double.MAX_VALUE);
        h1.setSpacing(6);
        h1.getChildren().addAll(v1,v2);
        
        VBox v3 = new VBox();//h1.setMaxWidth(Double.MAX_VALUE);
        v3.setSpacing(6);
        v3.setPadding(new Insets(8, 8, 8, 8));
        v3.getChildren().addAll(l_proyecto,h1);

        sc_inicio = new Scene(v3);
    }

    // REVISADO
    private void initAddDato()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_llave = new Label("llave: ");
        Label l_colum = new Label("columna: ");
        Label l_valor = new Label("dato: ");
        TextField tf_tabla = new TextField();
        TextField tf_llave = new TextField();
        TextField tf_colum = new TextField();
        TextField tf_valor = new TextField();
        Button b_accion = new Button("INGRESAR");

        b_accion.setOnAction((e) -> 
            {Interacciones.setDato(tf_tabla.getText(),tf_llave.getText(),tf_colum.getText(),tf_valor.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);
        //grid.setPrefWidth(400);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_llave, 0, 1);
        grid.add(tf_llave, 1, 1);
        grid.add(l_colum, 0, 2);
        grid.add(tf_colum, 1, 2);
        grid.add(l_valor, 0, 3);
        grid.add(tf_valor, 1, 3);
        grid.add(b_accion, 0, 4, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); //HPos.RIGHT

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_add_valor = new Scene(grid);
    }

    private void abrirAddDato()
    {
        Stage stage = new Stage();
        stage.setTitle("Ingresar dato");

        stage.setScene(sc_add_valor);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initDeleteDato()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_llave = new Label("llave: ");
        Label l_colum = new Label("columna: ");
        TextField tf_tabla = new TextField();
        TextField tf_llave = new TextField();
        TextField tf_colum = new TextField();
        Button b_accion = new Button("ELIMINAR");

        b_accion.setOnAction(
            (e) -> {Interacciones.deleteDato(tf_tabla.getText(),tf_llave.getText(),tf_colum.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);
        //grid.setPrefWidth(400);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_llave, 0, 1);
        grid.add(tf_llave, 1, 1);
        grid.add(l_colum, 0, 2);
        grid.add(tf_colum, 1, 2);
        grid.add(b_accion, 0, 3, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); //HPos.RIGHT

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_del_valor = new Scene(grid);
    }

    private void abrirDelDato()
    {
        Stage s = new Stage();
        s.setTitle("Eliminar dato");

        s.setScene(sc_del_valor);
        s.sizeToScene();

        s.show();
    }

    // REVISADO
    private void initAddTabla()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        TextField tf_tabla = new TextField();
        Button b_accion = new Button("AGREGAR");

        b_accion.setOnAction((e) -> {Interacciones.addTabla(tf_tabla.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(b_accion, 0, 1, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); //HPos.RIGHT

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_add_tabla = new Scene(grid);
    }

    private void abrirAddTabla()
    {
        Stage stage = new Stage();
        stage.setTitle("Agregar tabla");

        stage.setScene(sc_add_tabla);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initOpenTabla()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        TextField tf_tabla = new TextField();
        Button b_accion = new Button("MOSTRAR VALORES");

        b_accion.setOnAction((e) -> {botonOpenTabla(tf_tabla.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(b_accion, 0, 1, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_open_table = new Scene(grid);
    }

    private void abrirOpenTabla()
    {
        Stage stage = new Stage();
        stage.setTitle("Mostrar valores");

        stage.setScene(sc_open_table);
        stage.sizeToScene();

        stage.show();
    }

    private void botonOpenTabla(String tabla_propuesta)
    {
        Tabla tabla_concreta = Interacciones.getTabla(tabla_propuesta);
        if(tabla_concreta == null) {return;}
        abrirShowDatos(tabla_concreta);
    }

    // REVISADO
    private void abrirShowDatos(Tabla tabla_concreta)
    {
        Label l_tabla = new Label(tabla_concreta.getNombre());
        TableView<ObservableList<String>> tv_valores = Utilidades.generarTabla(tabla_concreta);
        //tv_valores.setPrefSize(400,200);
        
        VBox vertical = new VBox();
        vertical.setPrefSize(400,200);
        vertical.setSpacing(6);
        vertical.setPadding(new Insets(8, 8, 8, 8));
        vertical.getChildren().addAll(l_tabla,tv_valores);

        sc_show_valores = new Scene(vertical);

        Stage stage = new Stage();
        stage.setTitle("Mostrar datos");

        stage.setScene(sc_show_valores);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initAddColumna()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_colum = new Label("nombre de columna: ");
        Label l_tipo = new Label("tipo de valores: ");
        TextField tf_tabla = new TextField();
        TextField tf_colum = new TextField();
        TextField tf_tipo = new TextField();
        Button b_accion = new Button("AGREGAR");

        b_accion.setOnAction((e) -> 
            {Interacciones.addColumna(tf_tabla.getText(),tf_colum.getText(),tf_tipo.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_colum, 0, 1);
        grid.add(tf_colum, 1, 1);
        grid.add(l_tipo, 0, 2);
        grid.add(tf_tipo, 1, 2);
        grid.add(b_accion, 0, 4, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); 

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_add_colum = new Scene(grid);
    }

    private void abrirAddColumna()
    {
        Stage stage = new Stage();
        stage.setTitle("Agregar columna");

        stage.setScene(sc_add_colum);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initAddRelacion()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_colum = new Label("columna: ");
        Label l_relac = new Label("relacion: ");
        TextField tf_tabla = new TextField();
        TextField tf_colum = new TextField();
        TextField tf_relac = new TextField();
        Button b_accion = new Button("AGREGAR");

        b_accion.setOnAction((e) -> 
            {Interacciones.setRelacion(tf_tabla.getText(),tf_colum.getText(),tf_relac.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_colum, 0, 1);
        grid.add(tf_colum, 1, 1);
        grid.add(l_relac, 0, 2);
        grid.add(tf_relac, 1, 2);
        grid.add(b_accion, 0, 4, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); //HPos.RIGHT

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_add_relac = new Scene(grid);
    }

    private void abrirAddRelacion()
    {
        Stage stage = new Stage();
        stage.setTitle("Agregar relacion");

        stage.setScene(sc_add_relac);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initSetLlavePrimaria()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_colum = new Label("columna: ");
        TextField tf_tabla = new TextField();
        TextField tf_colum = new TextField();
        Button b_accion = new Button("FIJAR");

        b_accion.setOnAction((e) -> 
            {Interacciones.setLlavePrimaria(tf_tabla.getText(),tf_colum.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);
        //grid.setPrefWidth(400);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_colum, 0, 1);
        grid.add(tf_colum, 1, 1);
        grid.add(b_accion, 0, 2, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_set_llave = new Scene(grid);
    }

    private void abrirSetLlavePrimaria()
    {
        Stage stage = new Stage();
        stage.setTitle("Fijar llave primaria");

        stage.setScene(sc_set_llave);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initAddLlave()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_llave = new Label("llave: ");
        TextField tf_tabla = new TextField();
        TextField tf_llave = new TextField();
        Button b_accion = new Button("INGRESAR");

        b_accion.setOnAction(
            (e) -> {Interacciones.addLlave(tf_tabla.getText(),tf_llave.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_llave, 0, 1);
        grid.add(tf_llave, 1, 1);
        grid.add(b_accion, 0, 2, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER); 

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_add_llave = new Scene(grid);
    }

    private void abrirAddLlave()
    {
        Stage stage = new Stage();
        stage.setTitle("Agregar llave");

        stage.setScene(sc_add_llave);
        stage.sizeToScene();

        stage.show();
    }

    // REVISADO
    private void initDelTupla()
    {
        Label l_tabla = new Label("nombre de tabla: ");
        Label l_llave = new Label("llave: ");
        TextField tf_tabla = new TextField();
        TextField tf_llave = new TextField();
        Button b_accion = new Button("ELIMINAR");

        b_accion.setOnAction(
            (e) -> {Interacciones.deleteLlave(tf_tabla.getText(),tf_llave.getText());});

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8, 8, 8, 8));
        grid.setHgap(6);
        grid.setVgap(6);

        grid.add(l_tabla, 0, 0);
        grid.add(tf_tabla, 1, 0);
        grid.add(l_llave, 0, 1);
        grid.add(tf_llave, 1, 1);
        grid.add(b_accion, 0, 2, 2, 1);

        GridPane.setHalignment(b_accion, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1);

        sc_del_llave = new Scene(grid);
    }

    private void abrirDelLlave()
    {
        Stage stage = new Stage();
        stage.setTitle("Eliminar tupla");

        stage.setScene(sc_del_llave);
        stage.sizeToScene();

        stage.show();
    }
 
    private void initCargarXML()
    {
        Label lAddTablas = new Label("Ingresos de tablas: ");
        Label lAddValores = new Label("Ingreso de datos: ");
        Label lDelValores = new Label("Eliminacion de datos: ");

        Button bAddTablas = new Button("CARGAR");
        Button bAddValores = new Button("CARGAR");
        Button bDelValores = new Button("CARGAR");

        TextField tfAddTablas = new TextField();
        TextField tfAddValores = new TextField();
        TextField tfDelValores = new TextField();
        
        GridPane p = new GridPane();
        p.setAlignment(Pos.CENTER);
        p.setPadding(new Insets(8, 8, 8, 8));
        p.setHgap(6);
        p.setVgap(6);
        //p.setPrefWidth(w1);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        p.getColumnConstraints().addAll(col1);        
        
        p.add(lAddTablas, 0, 0);
        p.add(bAddTablas, 1, 0);
        p.add(tfAddTablas, 0, 1, 2, 1);
        p.add(lAddValores, 0, 2);
        p.add(bAddValores, 1, 2);
        p.add(tfAddValores, 0, 3, 2, 1);
        p.add(lDelValores, 0, 4);
        p.add(bDelValores, 1, 4);
        p.add(tfDelValores, 0, 5, 2, 1);
        
        GridPane.setHalignment(bAddTablas, HPos.RIGHT);
        GridPane.setHalignment(bAddValores, HPos.RIGHT);
        GridPane.setHalignment(bDelValores, HPos.RIGHT);

        bAddTablas.setOnAction( (e) -> { CargarXML.addTablas(tfAddTablas.getText());} );
        bAddValores.setOnAction( (e) -> { CargarXML.addDatos(tfAddValores.getText());} );
        bDelValores.setOnAction( (e) -> { CargarXML.delDatos(tfDelValores.getText());} );

        sc_add_xml = new Scene(p);
    }

    private void abrirCargarXML()
    {
        Stage stage = new Stage();
        stage.setTitle("Cargar XML");

        stage.setScene(sc_add_xml);
        stage.sizeToScene();

        stage.show();
    }
    
    private void abrirShowDiagrama() throws Exception
    {
        Image graph = new Image(Utilidades.getUbicacionDeGrafico());
        ImageView ivGraph = new ImageView(graph);
        HBox h1 = new HBox(ivGraph);

        sc_show_diagrama = new Scene(h1);

        Stage stage = new Stage();
        stage.setTitle("Diagrama ER");

        stage.setScene(sc_show_diagrama);
        stage.sizeToScene();

        stage.show();
    }

    private void abrirAyuda()
    {
        String ayuda = "Las operaciones en la Base de Datos deben seguir el orden:\n" + 
        "1- agregar Tabla\n" + 
        "2- agregar Columna\n" + 
        "3- fijar Llave Primaria de Tabla\n" + 
        "4- crear una Relacion (opcional)\n" + 
        "5- agregar una Llave\n" + 
        "6- agregar Dato\n" + 
        "7- quitar Dato \n" + 
        "8- quitar Tupla\n\n" +
        
        "Lo anterior tambien aplica cuando se quiere cargar operaciones con documentos XML.\n" + 
        
        "Sintaxis para agregar tablas:\n" + 
        "<estructura> <!-- [0,...] veces -->\n" + 
        "    <tabla> _NombreTabla_ </tabla>\n" + 
        "    <clave> _NombreColumna_ </clave>\n" + 
        "    <_Columna_> _TipoDato_ </_Columna_> <!-- <_Relacion_> --> <!-- [0,...] veces -->\n" + 
        "</estructura>\n\n" + 
        
        "Sintaxis para agregar columnas:\n" + 
        "<_Tabla_> <!-- [0,...] veces -->\n" + 
        "	<_ColumnaID_> _llave_ </_ColumnaID_>\n" + 
        "	<_Columna_> _valor_ </_Columna_> <!-- [0,...] veces -->\n" + 
        "</_Tabla_>\n\n" + 
        
        "Sintaxis para quitar tuplas:\n" + 
        "<_Tabla_> <!-- [0,...] veces -->\n" + 
        "	<_ColumnaID_> _llave_ </_ColumnaID_>\n" + 
        "</_Tabla_>\n";
        
        
        TextArea aAyuda = new TextArea(ayuda);
        
        HBox h1 = new HBox(aAyuda);

        sc_show_diagrama = new Scene(h1);

        Stage stage = new Stage();
        stage.setTitle("Diagrama ER");

        stage.setScene(sc_show_diagrama);
        stage.sizeToScene();

        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
