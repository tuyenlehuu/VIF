export class Branch{
    id:number;
    code:string;
    name:string;
    activeFlg:number;
    username:string
    password: string;
    
    public constructor(){
        this.activeFlg=-1;
    }

}