package sergio.sastre.multiplying.quality.of.texteditor

import sergio.sastre.multiplying.quality.of.texteditor.model.validators.*
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.broken.ContainsUpperCaseLetterValidator

class BrokenStrongPasswordValidator : RequirementValidator by
PasswordValidator(
    ContainsUpperCaseLetterValidator(), // Upper case validator is broken
    MinCharsValidator(6),
    ContainsDigitValidator(),
    ContainsLowerCaseLetterValidator(),
    NoBlanksValidator(),
)