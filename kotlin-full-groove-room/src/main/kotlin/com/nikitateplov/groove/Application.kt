package com.nikitateplov.groove

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.*

private data class Record(
    val title: String,
    val genre: String,
    val description: String,
    val price: Int,
    val image: String
)

private val records = listOf(
    Record(
        title = "The Beatles — Abbey Road",
        genre = "rock",
        description = "Классика британского рока.",
        price = 3990,
        image = "https://avatars.mds.yandex.net/i?id=f762968bb5ddb9abf032b6129a3642eabd3bd035-4551895-images-thumbs&n=13"
    ),
    Record(
        title = "Miles Davis — Kind of Blue",
        genre = "jazz",
        description = "Легендарный джазовый альбом.",
        price = 4500,
        image = "https://avatars.mds.yandex.net/i?id=0925296a1bc04c8b128d1a883f2f2b8d907d5cb1-2744668-images-thumbs&n=13"
    ),
    Record(
        title = "James Brown — Live",
        genre = "funk",
        description = "Фанк, энергия и живой звук.",
        price = 2990,
        image = "https://avatars.mds.yandex.net/i?id=a9768ce6e776562c60a16c4ad7861b749ac96770-5235721-images-thumbs&n=13"
    ),
    Record(
        title = "Queen — Bohemian Rhapsody",
        genre = "collectible",
        description = "Коллекционное издание Queen.",
        price = 6500,
        image = "https://avatars.mds.yandex.net/i?id=aeebd74190e456c01ae3fd13a0262903f6651f66-12728486-images-thumbs&n=13"
    )
)

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    meta(charset = "UTF-8")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    title("The Groove Room — Kotlin")
                    style { unsafe { +styles() } }
                }
                body {
                    header {
                        div("logo") {
                            h1 { +"THE GROOVE ROOM" }
                            p { +"NikitaTeplov Kotlin Edition" }
                        }
                        nav {
                            a("#home") { +"Главная" }
                            a("#catalog") { +"Каталог" }
                            a("#contacts") { +"Контакты" }
                            a("#cart") { +"Корзина" }
                        }
                    }

                    section("hero") {
                        id = "home"
                        h2 { +"Найди свой ритм" }
                        p { +"Винил, кассеты и редкие музыкальные издания" }
                        a("#catalog", classes = "hero-btn") { +"Смотреть каталог" }
                    }

                    main("main-content") {
                        aside("sidebar") {
                            h3 { +"Жанры" }
                            div("genres") {
                                genreLink("Все", "all")
                                genreLink("Рок", "rock")
                                genreLink("Джаз", "jazz")
                                genreLink("Фанк", "funk")
                                genreLink("Коллекционные", "collectible")
                            }
                        }

                        section("products-section") {
                            id = "catalog"
                            div("section-title") {
                                h2 { +"Каталог" }
                                p { +"Та же визуальная идея, но страница собрана Kotlin-кодом." }
                            }
                            div("product-grid") {
                                records.forEach { record -> productCard(record) }
                            }
                        }
                    }

                    section("about") {
                        h2 { +"О магазине" }
                        p { +"The Groove Room — магазин винила для любителей музыки. Этот вариант сделан как Kotlin/Ktor-приложение." }
                    }

                    section("cart-preview") {
                        id = "cart"
                        h2 { +"Корзина" }
                        p { +"В Kotlin-версии заказ оформляется через кнопку у товара — без отдельных JS/CSS/HTML-файлов." }
                    }

                    section("contacts") {
                        id = "contacts"
                        h2 { +"Контакты" }
                        p { +"Почта для связи:" }
                        a("mailto:Durak20009@yandex.ru") { +"Durak20009@yandex.ru" }
                    }

                    footer { p { +"© 2026 THE GROOVE ROOM — Kotlin project" } }
                }
            }
        }

        get("/health") {
            call.respondText("OK", ContentType.Text.Plain)
        }
    }
}

private fun FlowContent.genreLink(text: String, value: String) {
    a(href = if (value == "all") "#catalog" else "#genre-$value", classes = if (value == "all") "genre active" else "genre") { +text }
}

private fun FlowContent.productCard(record: Record) {
    article("product-card") {
        id = "genre-${record.genre}"
        img(src = record.image, alt = record.title)
        h3 { +record.title }
        p("description") { +record.description }
        p("price") { +"${record.price} ₽" }
        a(
            href = "mailto:Durak20009@yandex.ru?subject=Заказ The Groove Room&body=${record.title} — ${record.price} ₽",
            classes = "btn"
        ) { +"Заказать" }
    }
}

private fun styles(): String = """
*{margin:0;padding:0;box-sizing:border-box}html{scroll-behavior:smooth}body{background:#151515;font-family:system-ui,'Segoe UI',Roboto,Arial,sans-serif;color:#eaeaea;line-height:1.5}a{font:inherit}header{display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:1rem;padding:1.4rem 2rem;background:#1b1b1b;border-bottom:1px solid #2a2a2a}.logo h1{font-size:1.7rem;letter-spacing:2px;font-weight:800;color:#f3cd81}.logo p{color:#9a9a9a;font-size:.9rem}nav{display:flex;align-items:center;flex-wrap:wrap;gap:1rem}nav a{color:#d6d6d6;text-decoration:none;font-weight:700}nav a:hover{color:#f3cd81}.hero{text-align:center;padding:5rem 2rem;background:linear-gradient(rgba(0,0,0,.55),rgba(0,0,0,.7)),url('https://images.unsplash.com/photo-1494232410401-ad00d5433cfa?auto=format&fit=crop&w=1600&q=80') center/cover;margin-bottom:2rem}.hero h2{font-size:clamp(2.2rem,6vw,4.5rem);font-weight:900;margin-bottom:.75rem;color:#fff}.hero p{font-size:1.15rem;color:#ddd;margin-bottom:1.5rem}.hero-btn,.btn{display:inline-block;background:#c9772e;color:#fff;text-decoration:none;border:none;padding:.8rem 1.2rem;border-radius:999px;font-weight:800;cursor:pointer}.main-content{display:flex;flex-wrap:wrap;max-width:1280px;margin:0 auto;padding:0 2rem 2rem;gap:2rem}.sidebar{flex:1;min-width:190px;background:#1a1a1a;padding:1.5rem 1rem;border:1px solid #2a2a2a;border-radius:16px;height:fit-content}.sidebar h3{font-size:1.3rem;font-weight:800;margin-bottom:1.2rem;border-left:4px solid #c9772e;padding-left:.75rem}.genres{display:flex;flex-direction:column;gap:.75rem}.genre{text-align:left;color:#eaeaea;text-decoration:none;padding:.7rem .8rem;background:#222;font-weight:700;border-radius:10px}.genre.active,.genre:hover{background:#3a2b1d;color:#f3e2c9}.products-section{flex:3;min-width:280px}.section-title{margin-bottom:1.2rem}.section-title h2{font-size:2rem}.section-title p{color:#aaa}.product-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:1.6rem}.product-card{background:#202020;border:1px solid #2a2a2a;padding:1rem 1rem 1.2rem;display:flex;flex-direction:column;border-radius:18px;transition:transform .2s,border-color .2s}.product-card:hover{transform:translateY(-4px);border-color:#c9772e}.product-card img{width:100%;aspect-ratio:1/1;object-fit:cover;background:#2a2a2a;border-radius:14px}.product-card h3{font-size:1.08rem;font-weight:800;margin:.9rem 0 .35rem}.description{color:#aaa;font-size:.92rem;min-height:42px}.price{font-size:1.2rem;font-weight:900;color:#f3cd81;margin:.8rem 0 .9rem}.btn{text-align:center;border-radius:10px;margin-top:auto}.about,.contacts,.cart-preview{background:#1f1f1f;text-align:center;padding:2.8rem 2rem;margin-top:1rem}.about h2,.contacts h2,.cart-preview h2{font-size:1.8rem;margin-bottom:.8rem}.about p,.contacts p,.cart-preview p{max-width:650px;margin:0 auto;color:#bdbdbd}.contacts a{color:#f3cd81;font-weight:800}footer{background:#1b1b1b;text-align:center;padding:1.5rem;color:#9a9a9a;font-size:.9rem}@media(max-width:700px){header{align-items:flex-start}nav{width:100%}.main-content{padding:0 1rem 2rem}}
""".trimIndent()
