export class Branch{
    id:number;
    branchCode:string;
    branchName:string;
    activeFlg :number;
    public constructor(){
        this.activeFlg=-1;
    }

}