
export class StockTracking {
  id: number;
  code: string;
  name: string;
  orginalPrice: number;
  currentPrice: number;
  targetPriceBuy: number;
  targetPriceSell: number;
  description: string;
  createDate: Date;
  lastUpdate: Date;
  status: number;
}