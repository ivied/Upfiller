import com.itextpdf.text.pdf.AcroFields
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import pl.allegro.finance.tradukisto.ValueConverters
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


private const val FIELD_NUMBER = "Document Number"
private const val FIELD_DATE = "Date"
private const val FIELD_HOURS_AMOUNT = "h_amount"
private const val FIELD_PRICE = "price"
private const val FIELD_TOTAL = "total"
private const val FIELD_GRAND_TOTAL = "grandTotal"
private const val FIELD_FROM_ENG = "fromEng"
private const val FIELD_TO_ENG = "toEng"
private const val FIELD_FROM_RUS = "fromRus"
private const val FIELD_TO_RUS = "toRus"
private const val FIELD_TOTAL_SUB_WORDS_EN = "totalSumWordsEn"
private const val FIELD_TOTAL_SUB_WORDS_RUS = "totalSumWordsRus"
val dateFormatterTableEN = DateTimeFormatter.ofPattern("YY.MM.dd")


class ParsePDF {
fun parsePDF(
    filename: String,
    currency: Currency,
    dateFrom: LocalDate,
    dateTo: LocalDate,
    sendDate: LocalDate,
    hours: Double,
    price: Double
) {

    val fileNameResult = StringBuilder().append(filename.substringBeforeLast('.', "" ))
        .append(dateFormatterTableEN.format(dateFrom) + "-" + dateFormatterTableEN.format(dateTo))
        .append("." + filename.substringAfterLast('.', "")).toString()

    val moneyFormat = DecimalFormat("#.00")
        val reader = PdfReader(filename)
        val stamper = PdfStamper(reader, FileOutputStream(fileNameResult) as OutputStream?)
        val form = stamper.acroFields
        form.removeXfa()
        setDateAndNumber(form, dateFrom, dateTo, sendDate)
        form.setField(FIELD_HOURS_AMOUNT, hours.toString())
        form.setField(FIELD_PRICE, currency.sign() + moneyFormat.format(price))
        val total = price * hours
        form.setField(FIELD_TOTAL, currency.sign() + moneyFormat.format(total))
        form.setField(FIELD_GRAND_TOTAL, currency.sign() + moneyFormat.format(total))


        form.setField(FIELD_TOTAL_SUB_WORDS_RUS, ValueConverters.RUSSIAN_INTEGER.asWords(total.toInt()).capitalize() + " " + currency.toRus())

        form.setField(FIELD_TOTAL_SUB_WORDS_EN, ValueConverters.ENGLISH_INTEGER.asWords(total.toInt()).capitalize() + " " + currency.toEng())

        stamper.setFormFlattening(true)
        stamper.close()
        reader.close()

        println("Done")
    }

    private fun setDateAndNumber(
        acroForm: AcroFields,
        dateFrom: LocalDate,
        dateTo: LocalDate,
        sendDate: LocalDate
    ) {
        val numberFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatted = sendDate.format(numberFormatter)
        acroForm.setField(FIELD_NUMBER, formatted)
        val dateFormatterRu = DateTimeFormatter.ofPattern("dd MMMM", Locale("ru"))
        val dateFormatterEN = DateTimeFormatter.ofPattern("MMMM YYYY", Locale.US)

        acroForm.setField(FIELD_DATE, sendDate.format(dateFormatterRu) + " / " + sendDate.format(dateFormatterEN))

        val dateFormatterTableRu = DateTimeFormatter.ofPattern("dd MMMM YYYY", Locale("ru"))
        val dateFormatterTableEN = DateTimeFormatter.ofPattern("dd MMMM YYYY", Locale.US)

        acroForm.setField(FIELD_FROM_ENG, dateFormatterTableEN.format(dateFrom))
        acroForm.setField(FIELD_TO_ENG, dateFormatterTableEN.format(dateTo))
        acroForm.setField(FIELD_FROM_RUS, dateFormatterTableRu.format(dateFrom))
        acroForm.setField(FIELD_TO_RUS, dateFormatterTableRu.format(dateTo))
    }
}

