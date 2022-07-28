package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator

import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.*
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.broken.ContainsUpperCaseLetterValidator

class BrokenStrongPasswordValidator : RequirementValidator by
PasswordValidator(
    ContainsUpperCaseLetterValidator(), // Upper case validator is broken
    MinCharsValidator(6),
    ContainsDigitValidator(),
    ContainsLowerCaseLetterValidator(),
    NoBlanksValidator(),
)