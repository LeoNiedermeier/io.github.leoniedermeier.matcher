package io.github.leoniedermeier.matcher;

class TreeBuilder {

    public static TreeEntry buildFrom(BaseMatcher<?> current) {
        TreeEntry entry = new TreeEntry(current);
        for (BaseMatcher<?> matcher : current.getChilds()) {
            entry.addTreeEntry(buildFrom(matcher));
        }
        return entry;
    }

    private TreeBuilder() {
        throw new AssertionError("No TreeBuilder instances for you!");
    }
}
