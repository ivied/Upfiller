package view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import javafx.stage.StageStyle

import tornadofx.*
import java.io.File
import java.time.LocalDate
import kotlin.concurrent.thread


class MainView : View("My View") {

    val controller: MainController by inject()

    override val root = form {
        vbox {

            val selectedCurrency = SimpleStringProperty()
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
                    
                    find<ProgressView>().openModal(stageStyle = StageStyle.UTILITY)


                }

            }
            spacing = 20.00

        }
    }


}

class ProgressView: View(){

    override val root = progressindicator {
        thread {
            prefHeight = 100.00
            prefWidth = 100.00
            for (i in 1..100) {
                Platform.runLater { progress = i.toDouble() / 100.0 }
                Thread.sleep(30)
            }
        }
    }
}

