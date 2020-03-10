package org.keb.samples.trial

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.kebish.core.Module
import org.kebish.core.Page
import org.kebish.core.kebConfig
import org.kebish.junit5.KebTest
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver

val config = kebConfig {
    WebDriverManager.firefoxdriver().setup()
    driver = { FirefoxDriver() }
    baseUrl = "http://localhost:8080/"
}

class SecondKeb : KebTest(config) {

    @Test
    fun `number of polls`() {

        // when - i am on HomePage
        to(::HomePage) {

            // then - there are two polls
            assertThat(polls).hasSize(2)
        }
    }

    @Test
    fun `contains poll with name 'First poll'`() {

        // when - I am on HomePage
        to(::HomePage) {
            val pollNames = polls.map { it.question }
            // then - There is poll with name 'First poll'
            assertThat(pollNames).contains("First poll")
        }

    }

    @Test
    fun `navigation menu`() {
        to(::HomePage) {
            menu.toLogin()
        }.via(LoginPage::class) {
            menu.toHome()
        }.via(HomePage::class) {

        }
    }


}

class HomePage : Page() {

    override fun url() = ""
    override fun at() = cssList(".poll-content").size > 0

    val menu by content { module(NavigationMenu(css(".app-header"))) }

    val polls by content {
        cssList(".poll-content").map { module(Poll(it)) }
    }
}

class LoginPage : Page() {

    override fun at() = css(".login-content")

    val menu by content { module(NavigationMenu(css(".app-header"))) }

}

class NavigationMenu(scope: WebElement) : Module(scope) {
    private val homeTitle by content {
        css(".app-title")
    }

    private val login by content {
        cssList(".ant-menu-item").find { it.text == "Login" }
    }
    private val signup by content {
        cssList(".ant-menu-item").find { it.text == "Signup" }
    }

    fun toLogin(): LoginPage {
        login!!.click()
        return at(::LoginPage)
    }

    fun toHome(): HomePage {
        homeTitle.click()
        return at(::HomePage)
    }
}

class Poll(scope: WebElement) : Module(scope) {

    val question by content { css(".poll-question").text }
}

