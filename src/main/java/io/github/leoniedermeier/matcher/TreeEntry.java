package io.github.leoniedermeier.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TreeEntry {

    private List<TreeEntry> childs;

    private boolean executed;

    private boolean invers;

    private boolean matched;

    private BaseMatcher<?> matcher;

    private Entry mismatch;

    private TreeEntry parent;

    public TreeEntry(BaseMatcher<?> matcher) {
        this.matcher = matcher;
    }

    public void addTreeEntry(TreeEntry treeEntry) {
        if (this.childs == null) {
            this.childs = new ArrayList<>();
        }
        this.childs.add(treeEntry);
        treeEntry.setParent(this);
    }

    public void applyRecursice(Consumer<TreeEntry> c) {
        c.accept(this);
        getChilds().forEach(c);
    }

    public List<TreeEntry> getChilds() {
        return this.childs == null ? Collections.emptyList() : this.childs;
    }

    @SuppressWarnings("squid:S1452") 
    public BaseMatcher<?> getMatcher() {
        return this.matcher;
    }

    public Entry getMismatch() {
        return this.mismatch;
    }

    public TreeEntry getParent() {
        return this.parent;
    }

    public boolean isExecuted() {
        return this.executed;
    }

    public boolean isInvers() {
        return this.invers;
    }

    public boolean isMatched() {
        return this.matched;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public void setInvers(boolean invers) {
        this.invers = invers;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setMismatch(Entry mismatch) {
        this.mismatch = mismatch;
    }

    public void setParent(TreeEntry parent) {
        this.parent = parent;
    }
}
