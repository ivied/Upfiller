import javafx.stage.Stage
import tornadofx.App
import view.MainView

class App : App(MainView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
    }


}