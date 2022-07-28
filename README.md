# Multiplying the quality of unit tests
An Android project in *Jetpack Compose* to showcase how to improve our unit tests step by step:
1. From tests *multiple assertions* to *parameterized example-based tests*,
2. Add extra *property-based tests* to catch regression bugs that *parameterized tests* cannot.
3. Advance use of *property-based tests* for stateful testing: 
  verify that the state of our model is correct after performing any action on it.

For 1. & 2, we test the logic behind the **strong password validator** of a [**create account**](https://github.com/sergio-sastre/Multiplying_the_quality_of_unit_tests/tree/master/passwordvalidator) screen.

<p align="center">
<img width="250" src="https://user-images.githubusercontent.com/6097181/169895372-f21adba4-5478-4bd4-8b9d-3676da049d4d.gif">
</p>


For 3, we test that the state of a [**text editor**](https://github.com/sergio-sastre/Multiplying_the_quality_of_unit_tests/tree/master/texteditor) (i.e. displayed text & text to display upon undo/redo actions) is correct after editing, undoing and redoing in any *random order*.
<br>
<p align="center">
<img width="250" src="https://user-images.githubusercontent.com/6097181/181569006-db7cee16-b200-4bc2-88ba-2e6ad2441a94.gif">
</p>

You can find the blog posts on the topic here:

1) [Better unit tests with Parameterized testing](https://sergiosastre.hashnode.dev/better-unit-tests-with-parameterized-testing)

2) [Writing bulletproof code with Property-Based testing](https://sergiosastre.hashnode.dev/writing-bulletproof-code-with-property-based-testing-pbt)

3) Super testing data models with stateful tests (coming soon...)

As well as the slides of my presentation at Droidcon Lisbon 2022 & Droidcon Berlin 2022

Slides - [Writing bulletproof code with Property-Based testing](https://speakerdeck.com/gio_sastre/writing-bulletproof-code-with-property-based-testing)
