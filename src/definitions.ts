export interface EasyAdsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
