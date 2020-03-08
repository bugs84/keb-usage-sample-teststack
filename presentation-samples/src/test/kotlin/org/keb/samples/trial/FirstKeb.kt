package org.keb.samples.trial

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.kebish.core.Configuration
import org.kebish.core.Module
import org.kebish.core.Page
import org.kebish.core.kebConfig
import org.kebish.core.module.TextInput
import org.kebish.core.module.TextLikeInput
import org.kebish.junit5.KebTest
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver

class FirstKeb : KebTest(ourKebConfig()) {


    @Test
    fun `succesfull login`() {
        // given - I am on LoginPage
        to(::HomePage) {
            appHeader.toLoginPage()
        }.via(LoginPage::class) {

            // when - fill correct login
            fillUserName("aaa")
            fillPassword("aaaaaa")
            // and - click login button
            loginButton.click()
        }

        // then - I am at home page
        at(::HomePage)
    }

    @Test
    fun `application contains two polls`() {
        // when - i am on home page
        to(::HomePage) {

            // then - page contains poll for 'dinner'
            assertThat(allPolls).hasSize(2)

        }
    }

    @Test
    fun `application contains 'first poll' question`() {
        // when - i am on home page
        to(::HomePage) {

            // then - page contains poll for 'dinner'
            val allQuestions = allPolls.map { it.question }
            assertThat(allQuestions).contains("First poll")

        }
    }

    private fun iAmSuccessfullyLoggedIn() {
        TODO("Not yet implemented")
    }

    private fun fillInCorrectCredential() {
        TODO("Not yet implemented")
    }

    private fun iAmOnLoginPage() {
        TODO("Not yet implemented")
    }

}

class HomePage() : Page() {
    override fun url() = ""

    override fun at() =  pollsContainer


    val appHeader by content { module(AppHeader(css(".app-header"))) }
    val pollsContainer by content { css(".polls-container") }
    val allPolls by content { cssList(".poll-content").map { module(Poll(it)) } }


}

class AppHeader(scope: WebElement) : Module(scope) {
    private val loginButton by content(required = false) {
        cssList(".ant-menu-item").first { it.text == "Login" }
    }

    fun isLoggedIn(): Boolean {
        return !loginButton.isDisplayed
    }

    fun toLoginPage(): LoginPage {
        loginButton.click()
        return at(::LoginPage)
    }
}

class LoginPage() : Page() {
    override fun url() = "/login"

    override fun at(): WebElement {
        return css(".login-container")
    }

    private val usernameInput by content {
        module(TextInput(css("#usernameOrEmail")))
    }

    private val passwordInput by content {
        module(PasswordInput(css("#password")))
    }

    val loginButton by content { css(".login-form-button") }

    fun fillUserName(username: String) {
        usernameInput.text = username
    }

    fun fillPassword(password: String) {
        passwordInput.text = password
    }

    fun clickLogin(): HomePage {
        loginButton.click()
        return at(::HomePage)
    }


}

class Poll(scope: WebElement) : Module(scope) {

    val question by content { css(".poll-question").text }
}


fun ourKebConfig(): Configuration {
    return kebConfig {
        WebDriverManager.firefoxdriver().setup()
        driver = { FirefoxDriver() }
        baseUrl = "http://localhost:8080"
    }
}


class PasswordInput(scope: WebElement) : TextLikeInput(scope) {
    override fun getInputType() = "password"
}