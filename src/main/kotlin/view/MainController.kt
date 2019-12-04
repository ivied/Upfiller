package view

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.Controller
import java.io.File
import java.time.LocalDate
import ParsePDF


class MainController: Controller() {
    val rate = SimpleStringProperty()
    val amountOfHours = SimpleStringProperty()
    var templates = FXCollections.observableArrayList("")
     val currencies = FXCollections.observableArrayList(Currency.DOLLAR.sign(), Currency.EURO.sign())
     val selectedTemplate = SimpleStringProperty()
     val selectedCurrency = SimpleStringProperty()
     val startDate = SimpleObjectProperty<LocalDate>()
     val endDate = SimpleObjectProperty<LocalDate>()
    val sendDate = SimpleObjectProperty<LocalDate>()


    fun addFolder(dir: File?) {
        dir?.walk()?.forEach {
            templates.add(it.path)
        }
    }

    fun parsePDF() {
        val currency = if (selectedCurrency.get().equals(Currency.DOLLAR.sign())) Currency.DOLLAR else Currency.EURO
        ParsePDF().parsePDF(selectedTemplate.get(), currency, startDate.get(), endDate.get(), sendDate.get(), amountOfHours.get().toDouble(), rate.get().toDouble() )

    }
}