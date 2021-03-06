package view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle

import tornadofx.*
import java.io.File
import java.time.LocalDate
import kotlin.concurrent.thread


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


            vbox {
                text("Invoice Send Date:")
                datepicker(controller.sendDate) {
                    promptText = "Invoice Send Date"

                }
            }

            vbox {
                text("Contract Start Date:")
                datepicker(controller.startDate) {
                    promptText = "Contract Start Date"

                }
            }

            vbox {
                text("Contract End Date:")
                datepicker(controller.endDate) {
                    promptText = "Contract End Date"

                }
            }
            hbox {
                textfield(controller.amountOfHours) {
                    prefWidth = 50.00
                    promptText = "Hours"
                    filterInput { it.controlNewText.isDouble() }
                }
                textfield(controller.rate) {
                    promptText = "Rate"
                    prefWidth = 50.00
                    filterInput { it.controlNewText.isDouble() }
                }
                combobox(controller.selectedCurrency, controller.currencies) {
                    prefWidth = 50.00

                }
                spacing = 10.00
            }
            combobox(controller.selectedTemplate, controller.templates){
                promptText = "Template"
                useMaxWidth = true
            }
            button("Generate") {
                action {
                    controller.parsePDF()

                    //find<ProgressView>().openModal(stageStyle = StageStyle.UTILITY)
                }
                style {
                    fontWeight = FontWeight.EXTRA_BOLD
                    useMaxWidth = true
                    borderColor += box(
                        top = Color.RED,
                        right = Color.DARKGREEN,
                        left = Color.ORANGE,
                        bottom = Color.PURPLE
                    )
                }
            }
            prefHeight = 350.0

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

