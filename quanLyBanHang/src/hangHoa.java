public class hangHoa
{
   public int hang_hoa_ID;
   public String ten_hang_hoa;
   public String nha_sx;
   public String loai_hang_hoa;
   public int nam_sx;
   hangHoa(){};

   public hangHoa(int hhi, String thh, String nsx, String lhh, int namsx)
   {
      hang_hoa_ID = hhi;
      ten_hang_hoa = thh;
      nha_sx = nsx;
      loai_hang_hoa = lhh;
      nam_sx = namsx;
   }
}
