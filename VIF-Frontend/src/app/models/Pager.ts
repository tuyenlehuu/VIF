export class Pager {
    page: number;
    pageSize: number;
    totalRow: number;
    firstPage: number;
    lastPage: number;

    public constructor() {
        this.page = 1;
        this.pageSize = 5;
    }
}