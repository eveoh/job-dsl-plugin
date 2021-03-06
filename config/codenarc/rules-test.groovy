ruleset {
    ruleset('rulesets/junit.xml') {
        // Spock ...
        exclude 'JUnitPublicNonTestMethod'
        // TODO: fix violations
        exclude 'JUnitPublicProperty'
    }

    ruleset('file:config/codenarc/rules.groovy') {
        // that's OK for test code
        exclude 'ComparisonWithSelf'
        // that's OK for test code
        exclude 'ExplicitCallToCompareToMethod'
        // that's OK for test code
        exclude 'ExplicitCallToEqualsMethod'
        // Spock encourages to violate this rule
        exclude 'MethodName'
        // Spock's data tables violate this rule
        exclude 'UnnecessaryBooleanExpression'
        // causes false negatives
        exclude 'UnusedObject'
    }
}
