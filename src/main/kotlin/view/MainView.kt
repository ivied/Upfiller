package view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import parsePDF
import tornadofx.*
import java.io.File

private const val FILE_NAME = "xsat"

private const val FILE_NAME_VAJUTO = "vajuto"
class MainView : View("My View") {

    val currencies = FXCollections.observableArrayList("$", "€")
    var templates = FXCollections.observableArrayList(FILE_NAME, FILE_NAME_VAJUTO)

    override val root = form {
        vbox {

            val selectedCurrency = SimpleStringProperty()
            hbox {


                button("Add Template Directory") {
                    action {
                        val dir = chooseDirectory("Select Target Directory" )
                        dir?.walk()?.forEach {
                            templates.add(it.path)
                        }
                    }
                    hboxConstraints {
                        marginRight = 10.0
                        hGrow = Priority.ALWAYS
                    }
                }


                button ("", imageview( File("settings-512.png").toURI().toString()){

                    fitHeight = 20.00
                    fitWidth = 20.00

                })

                vboxConstraints {
                    marginBottom = 20.0
                    vGrow = Priority.ALWAYS
                }
            }
            val selectedTemplate = SimpleStringProperty()
            combobox(selectedCurrency, currencies){
                prefWidth = 100.00
                vboxConstraints {
                    marginBottom = 20.0
                    vGrow = Priority.ALWAYS
                }
            }
            combobox(selectedTemplate, templates){
                prefWidth = 100.00
                vboxConstraints {
                    marginBottom = 20.0
                    vGrow = Priority.ALWAYS
                }
            }
            button("Generate") {
                action {
                    parsePDF(selectedTemplate.get(), selectedCurrency.get())
                    
                }

            }

        }
    }


}

