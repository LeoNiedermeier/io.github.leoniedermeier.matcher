package io.github.leoniedermeier.matcher.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BaseMatcherState {

    private List<BaseMatcherState> childs;

    private boolean executed;

    private final BaseMatcher<?> matcher;

    private Message mismatch;

    private BaseMatcherState parent;

    public BaseMatcherState(BaseMatcher<?> matcher) {
        this.matcher = matcher;
    }

    public void acceptRecursice(Consumer<BaseMatcherState> c) {
        c.accept(this);
        getChilds().forEach(child -> child.acceptRecursice(c));
    }

    public void addChild(BaseMatcherState child) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(child);
        child.setParent(this);
    }

    public Optional<BaseMatcherState> findFirstChild(Predicate<BaseMatcherState> predicate) {
        return getChilds().stream().filter(predicate).findFirst();
    }

    @SuppressWarnings("squid:S1452")
    public BaseMatcher<?> getMatcher() {
        return matcher;
    }

    public Message getMismatch() {
        return mismatch;
    }

    public BaseMatcherState getParent() {
        return parent;
    }

    public boolean isExecuted() {
        return executed;
    }

    public boolean isMatched() {
        return mismatch == null;
    }

    public void setExecuted() {
        // clear children
        acceptRecursice((BaseMatcherState t) -> {
            t.executed = false;
            t.mismatch = null;
        });
        executed = true;
    }

    public void setMismatch(Message mismatch) {
        this.mismatch = mismatch;
    }

    public void setParent(BaseMatcherState parent) {
        this.parent = parent;
    }

    private List<BaseMatcherState> getChilds() {
        // Collection encapsulated, not meant for outside use
        return childs == null ? Collections.emptyList() : childs;
    }
}
