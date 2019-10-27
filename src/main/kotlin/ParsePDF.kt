import com.itextpdf.text.pdf.AcroFields
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val FIELD_NUMBER = "Document Number"
private const val FILE_NAME = "vajuto1.pdf"
private const val FILE_NAME_RESULT = "filename1.pdf"
private const val FIELD_DATE = "Date"
//private const
//private const


fun main(args: Array<String>) {
    println("Hello World")

    val reader = PdfReader(FILE_NAME)
    val stamper = PdfStamper(reader, FileOutputStream(FILE_NAME_RESULT) )
    val form = stamper.acroFields
    form.removeXfa()
    setDateAndNumber(form)
    stamper.close()
    reader.close()

}

private fun setDateAndNumber(acroForm: AcroFields) {
    val current = LocalDateTime.now()
    val numberFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val formatted = current.format(numberFormatter)
    acroForm.setField(FIELD_NUMBER, formatted)
    val dateFormatterRu = DateTimeFormatter.ofPattern("dd MMMM", Locale("ru"))
    acroForm.setField(FIELD_DATE, current.format(dateFormatterRu))


}
