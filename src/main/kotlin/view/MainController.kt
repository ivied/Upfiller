package view

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.Controller
import java.io.File
import java.time.LocalDate
import ParsePDF



class MainController: Controller() {
     var templates = FXCollections.observableArrayList("")
     val currencies = FXCollections.observableArrayList("$", "€")
     val selectedTemplate = SimpleStringProperty()
     val selectedCurrency = SimpleStringProperty()
     val startDate = SimpleObjectProperty<LocalDate>()
     val endDate = SimpleObjectProperty<LocalDate>()


    fun addFolder(dir: File?) {
        dir?.walk()?.forEach {
            templates.add(it.path)
        }
    }

    fun parsePDF() {
        ParsePDF().parsePDF(selectedTemplate.get(), selectedCurrency.get(), startDate.get(), endDate.get())

    }
}