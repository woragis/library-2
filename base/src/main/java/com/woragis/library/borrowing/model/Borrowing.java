package com.woragis.library.borrowing.model;

import java.util.Date;

public class Borrowing {
    public int id;
    public int exemplarId;
    public int userId;
    public int employeeId;
    public Date lendingDate;
    public Date expectedReturnDate;
    public Date actualReturnDate;
}
