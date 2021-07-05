package sergio.sastre.multiplying.quality.of.unittests.pbt

import com.google.common.truth.Truth
import org.junit.Assert

fun String?.assertNullOrDoesNotContain(toBeContained: String) {
    if (this == null) {
        Assert.assertTrue(true)
    } else {
        Truth.assertThat(this).doesNotContain(toBeContained)
    }
}