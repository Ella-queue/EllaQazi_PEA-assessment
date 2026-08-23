package ca.qc.cegepheritage.g30a2

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class CafeApplication : Application() {
    override fun start(stage: Stage) {
        val model = CafeModel()
        val view = CafeView()
        val presenter = CafePresenter(model, view)
        view.presenter = presenter
        stage.scene = Scene(view.root, 800.0, 500.0)
        stage.title = "Kat's Cat Cafe\uD83D\uDE38"
        stage.show()
        presenter.start()
    }
}

fun main() {
    Application.launch(CafeApplication::class.java)
}