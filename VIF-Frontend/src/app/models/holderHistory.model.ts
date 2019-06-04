import { HolderEquity } from './HolderEquity';
export class HolderHistory {
  id:number;
  amount:number;
  price:number;
  lastUpdate:Date;
  reason:string;
  description:string;
  holder:HolderEquity

    public constructor() {
       this.holder=new HolderEquity();
        
    }
}