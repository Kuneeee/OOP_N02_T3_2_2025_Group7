public class hangHoa
{
   public int hang_hoa_ID;
   public String ten_hang_hoa;
   public String nha_sx;
   public int nam_sx;
   public String loai_hang_hoa;
   hangHoa(){};
   public hangHoa(int hhi, String thh, String nsx, int namsx, String lhh)
   {
       hang_hoa_ID = hhi;
       ten_hang_hoa = thh;
       nha_sx = nsx;
       nam_sx = namsx;
       loai_hang_hoa = lhh;
   }
}
