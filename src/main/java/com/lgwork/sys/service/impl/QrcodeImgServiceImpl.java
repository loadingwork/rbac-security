package com.lgwork.sys.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgwork.base.constant.CustomConst;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.ext.FileStorageProperties;
import com.lgwork.domain.dto.QrcodeImgRespDTO;
import com.lgwork.domain.po.QrcodeImgPO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.sys.dao.QrcodeImgDAO;
import com.lgwork.sys.service.QrcodeImgService;
import com.lgwork.util.QrcodeUtil;
import com.lgwork.util.UuidUtil;

/**
 * 二维码实现
 * @author irays
 *
 */
@Service
public class QrcodeImgServiceImpl implements QrcodeImgService {
	
	
	/**
	 * 二维码持久化接口
	 */
	@Autowired
	private QrcodeImgDAO qrcodeImgDAO;
	
	/**
	 * 文件配置
	 */
	@Autowired
	private FileStorageProperties fileStorageProperties;
	
	

	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<QrcodeImgRespDTO> saveQrcodeImgPO(Integer width, Integer height, String content) throws Exception {
		
		if (StringUtils.isEmpty(content) || width <= 10 || height <= 10 ) {
			// 违法参数
			return new BaseResultBuilder<QrcodeImgRespDTO>()
					.enums(ErrorCodeEnum.MISSING_KEY_PARAMETERS).build();
		}
		
		// 生成二维码
		byte[] in = QrcodeUtil.createQrcodeStream(content, width, height);
		
		// --- 常量区
		final long currentTimeMillis = System.currentTimeMillis();
		final String code = UuidUtil.uuid32();
		
		QrcodeImgPO qrcodeImgPO = new QrcodeImgPO();
		
		// 初始化
//		BasePO.initData(qrcodeImgPO);
		
		// 获取文件key
		String fileKey =  fileStorageProperties.localQrcodeRelativeFileKey(code);
		
		// 构建访问url
		String url = fileStorageProperties.getQrcodeUrl() + fileKey;
		
		// 构建文件绝对路径
		String fileAbsolutePath = fileStorageProperties.getQrcodeRoot() + fileKey;
		
		qrcodeImgPO.setCode(code);
		qrcodeImgPO.setFileKey(fileKey);
		qrcodeImgPO.setContent(content);
		qrcodeImgPO.setType("tmp");
		qrcodeImgPO.setExpiresAt(new Date(currentTimeMillis + CustomConst.TIME_S3600));
		qrcodeImgPO.setUsed(false);
		
		// 保存
		qrcodeImgDAO.save(qrcodeImgPO);
		
		// 开始保存文件
		FileUtils.copyInputStreamToFile(new ByteArrayInputStream(in), 
				new File(fileAbsolutePath));
		
		// 构建返回值
		QrcodeImgRespDTO qrcodeImgRespDTO = new QrcodeImgRespDTO();
		qrcodeImgRespDTO.setCode(code);
		qrcodeImgRespDTO.setUrl(url);
		
		// 返回图片的地址
		return new BaseResultBuilder<QrcodeImgRespDTO>().data(qrcodeImgRespDTO).build();
	}

	@Override
	public QrcodeImgPO findBycode(String code) {
		
		QrcodeImgPO qrcodeImgPO = qrcodeImgDAO.findByCode(code);
		
		return qrcodeImgPO;
	}
	
	
	
	

}
