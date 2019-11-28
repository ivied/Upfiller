package view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.File
import java.time.LocalDate


class MainView : View("My View") {

    val controller: MainController by inject()

    override val root = form {
        vbox {

            hbox {


                button("Add Template Directory") {
                    action {
                        val dir = chooseDirectory("Select Target Directory" )
                        controller.addFolder(dir)
                    }
                }


                button ("", imageview( File("settings-512.png").toURI().toString()){
                    fitHeight = 20.00
                    fitWidth = 20.00
                })

                spacing = 10.00
            }
            datepicker(controller.startDate) {
                value = LocalDate.now()
            }
            datepicker(controller.endDate) {
                value = LocalDate.now()
            }
            combobox(controller.selectedCurrency, controller.currencies){
                prefWidth = 100.00
            }
            combobox(controller.selectedTemplate, controller.templates){
                prefWidth = 100.00
            }
            button("Generate") {
                action {
                    controller.parsePDF()
                    
                }

            }
            spacing = 20.00

        }
    }


}

