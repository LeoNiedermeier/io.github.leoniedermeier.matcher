package io.github.leoniedermeier.matcher;

public class ExecutionContext {

    private Object actual;

    private boolean invers = false;

    private TreeEntry treeEntry;

    public ExecutionContext() {
        super();
    }

    public Object getActual() {
        return this.actual;
    }

    public TreeEntry getTreeEntry() {
        return this.treeEntry;
    }

    public boolean isInvers() {
        return this.invers;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }

    public void setMatched(boolean matched) {
        getTreeEntry().setMatched(matched);
    }

    public void setMismatch(String text, Object... arguments) {
        getTreeEntry().setMismatch(new Entry(text, arguments));
    }

    public void setTreeEntry(TreeEntry treeEntry) {
        this.treeEntry = treeEntry;
    }

    public void switchInvers() {
        this.invers = !this.invers;
    }
}
