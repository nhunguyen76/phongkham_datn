package vn.toancauxanh.cms.service;

import java.util.HashMap;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QSach;
import vn.toancauxanh.gg.model.Sach;
import vn.toancauxanh.service.BasicService;

public class SachService extends BasicService<Sach>{

	public JPAQuery<Sach> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<Sach> query = find(Sach.class);
		
		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			query.where(QSach.sach.tieuDe.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			query.where(QSach.sach.trangThai.eq(trangThai));
		}
		query.orderBy(QSach.sach.ngaySua.desc());
		
		
		return query;
	}
	
	static HashMap<Integer, Double> sim = new HashMap<Integer, Double>();
    static long sachCount;
	
	///////////////
//	private static Map<Sach, Map<Sach, Double>> diff = new HashMap<>();
//    private static Map<Sach, Map<Sach, Integer>> freq = new HashMap<>();
//    private static Map<NhanVien, HashMap<Sach, Double>> inputData = new HashMap<>();
//    private static Map<NhanVien, HashMap<Sach, Double>> outputData = new HashMap<>();
//    
//    private static void buildDifferencesMatrix(Map<NhanVien, HashMap<Sach, Double>> data) {
//        for (HashMap<Sach, Double> user : data.values()) {
//            for (Entry<Sach, Double> e : user.entrySet()) {
//                if (!diff.containsKey(e.getKey())) {
//                    diff.put(e.getKey(), new HashMap<Sach, Double>());
//                    freq.put(e.getKey(), new HashMap<Sach, Integer>());
//                }
//                for (Entry<Sach, Double> e2 : user.entrySet()) {
//                    int oldCount = 0;
//                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
//                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
//                    }
//                    double oldDiff = 0.0;
//                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
//                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
//                    }
//                    double observedDiff = e.getValue() - e2.getValue();
//                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
//                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
//                }
//            }
//        }
//        for (Sach j : diff.keySet()) {
//            for (Sach i : diff.get(j).keySet()) {
//                double oldValue = diff.get(j).get(i).doubleValue();
//                int count = freq.get(j).get(i).intValue();
//                diff.get(j).put(i, oldValue / count);
//            }
//        }
//        printData(data);
//    }
//    
//    private void predict(Map<NhanVien, HashMap<Sach, Double>> data) {
//        HashMap<Sach, Double> uPred = new HashMap<Sach, Double>();
//        HashMap<Sach, Integer> uFreq = new HashMap<Sach, Integer>();
//        for (Sach j : diff.keySet()) {
//            uFreq.put(j, 0);
//            uPred.put(j, 0.0);
//        }
//        for (Entry<NhanVien, HashMap<Sach, Double>> e : data.entrySet()) {
//            for (Sach j : e.getValue().keySet()) {
//                for (Sach k : diff.keySet()) {
//                    try {
//                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
//                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
//                        uPred.put(k, uPred.get(k) + finalValue);
//                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
//                    } catch (NullPointerException e1) {
//                    }
//                }
//            }
//            HashMap<Sach, Double> clean = new HashMap<Sach, Double>();
//            for (Sach j : uPred.keySet()) {
//                if (uFreq.get(j) > 0) {
//                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
//                }
//            }
//            
//            List<Sach> listSach = find(Sach.class).fetch();	//
//            for (Sach j : listSach) {
//                if (e.getValue().containsKey(j)) {
//                    clean.put(j, e.getValue().get(j));
//                } else {
//                    clean.put(j, -1.0);
//                }
//            }
//            outputData.put(e.getKey(), clean);
//        }
//        printData(outputData);
//    }
//    
//    private static void printData(Map<NhanVien, HashMap<Sach, Double>> data) {
//        for (NhanVien user : data.keySet()) {
//            System.out.println(user.getHoVaTen() + ":");
//            print(data.get(user));
//        }
//    }
//    
//    private static void print(HashMap<Sach, Double> hashMap) {
//        NumberFormat formatter = new DecimalFormat("#0.000");
//        for (Sach j : hashMap.keySet()) {
//            System.out.println(" " + j.getTieuDe() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
//        }
//    }
}
