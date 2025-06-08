package com.woragis.library.physicalcopy.model;

import com.woragis.library.shared.enums.BookStatus;

public class PhysicalCopy {
    public int id;
    public int livroId;
    public BookStatus status;
    public int currentOwner;
}
