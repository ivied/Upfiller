package view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import parsePDF
import tornadofx.*
import kotlin.concurrent.thread

private const val FILE_NAME = "xsat"

private const val FILE_NAME_VAJUTO = "vajuto"
class MainView : View("My View") {

    val currencies = FXCollections.observableArrayList("$", "€")
    val templates = FXCollections.observableArrayList(FILE_NAME, FILE_NAME_VAJUTO)

    override val root = form {
        vbox {

            val selectedCurrency = SimpleStringProperty()

            val selectedTemplate = SimpleStringProperty()
            combobox(selectedCurrency, values = currencies){
                prefWidth = 100.00
                vboxConstraints {
                    marginBottom = 20.0
                    vGrow = Priority.ALWAYS
                }
            }
            combobox(selectedTemplate, values= templates){
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

