import { Asset } from './Asset.model';
export class TransactionHistory {
    id:number;
    amount: number;
    price:number;
    typeOfTransaction:string;
    feeType:string;
    description:string;
    activeFlg:number;
    createDate:Date;
    lastUpdate:Date;
    asset:Asset;
    status:string;

    public constructor() {
        this.asset = new Asset(); 
    }
}