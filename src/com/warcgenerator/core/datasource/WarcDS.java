package com.warcgenerator.core.datasource;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.archive.io.ArchiveRecord;
import org.archive.io.arc.ARCConstants;
import org.archive.io.warc.WARCReader;
import org.archive.io.warc.WARCReaderFactory;
import org.archive.io.warc.WARCWriter;
import org.archive.io.warc.WARCWriterPoolSettings;
import org.archive.uid.RecordIDGenerator;
import org.archive.uid.UUIDGenerator;
import org.archive.util.ArchiveUtils;
import org.archive.util.anvl.ANVLRecord;

import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.CloseException;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.exception.datasource.WriteException;
import com.warcgenerator.core.helper.FileHelper;

public class WarcDS extends DataSource implements IDataSource {
	public static final String DS_TYPE = "WarcDS";
	
	private static final String URL_TAG = "urlTag";
	
	@SuppressWarnings("unused")
	private OutputWarcConfig config;
	private WARCWriter writer;
	private WARCReader reader;
	private Iterator<ArchiveRecord> archIt;
	private File warc;

	private static Logger logger = Logger.getLogger(WarcDS.class);

	/**
	 * Open a Warc datasource in read mode
	 * 
	 * @param path
	 * @throws DSException
	 */
	public WarcDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		try {
			logger.info("Openning file: " + dsConfig.getFilePath());
			reader = WARCReaderFactory.get(dsConfig.getFilePath());
	
			archIt = reader.iterator();
		} catch (IOException e) {
			throw new OpenException(e);
		}
	}

	/**
	 * Open a Warc datasource in write mode
	 * 
	 * @param config
	 * @throws DSException
	 */
	public WarcDS(OutputWarcConfig config) throws DSException {
		this.config = config;

		warc = new File(config.getFileName());

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(warc));

			List<String> metadata = new ArrayList<String>(1);
			// metadata.add("something");

			writer = new WARCWriter(new AtomicInteger(), bos, warc,
					getSettings(false, "", null, metadata));

			// Write a warcinfo record with description about how this WARC
			// was made.
			// If you want to write something in the head of warc
			// writer.writeWarcinfoRecord(warc.getName(), "toConfigure");
		} catch (IOException e) {
			throw new OpenException(e);
		}

	}

	private WARCWriterPoolSettings getSettings(final boolean isCompressed,
			final String prefix, final List<File> arcDirs,
			final List<String> metadata) {
		return new WARCWriterPoolSettings() {
			public List<File> calcOutputDirs() {
				return arcDirs;
			}

			public boolean getCompress() {
				return isCompressed;
			}

			public boolean getFrequentFlushes() {
				return false;
			}

			public long getMaxFileSizeBytes() {
				return ARCConstants.DEFAULT_MAX_ARC_FILE_SIZE;
			}

			public List<String> getMetadata() {
				return metadata;
			}

			public String getPrefix() {
				return prefix;
			}

			public String getTemplate() {
				return "${prefix}-${timestamp17}-${serialno}";
			}

			public int getWriteBufferSize() {
				return 4096;
			}

			public RecordIDGenerator getRecordIDGenerator() {
				return new UUIDGenerator();
			}

		};
	}

	public DataBean read() throws DSException {
		DataBean dataBean = null;
		ArchiveRecord ar = null;
		boolean skip = false;
		
		do {
			skip = false;
			if (archIt.hasNext()) {
				ar = archIt.next();
				//System.out.println("faltan: " + );
			}
			if (ar != null) {
				// Get the filename
				String url = (String) ar.getHeader()
						.getHeaderFields().get(((CustomParamConfig)this.getDataSourceConfig().getCustomParams()
								.get(URL_TAG)).getValue());
				if (url == null || url.equals("")) {
					skip = true;
				}
				
				if (skip == false) {
					dataBean = new DataBean();
					dataBean.setUrl(url);
					dataBean.setSpam(this.getDataSourceConfig().getSpam());
					dataBean.setData(ar);
					dataBean.setTypeDS(DS_TYPE);
					
					StringBuffer sb = new StringBuffer();
					try {
						for (byte[] buffer = new byte[1024];
								ar.read(buffer) != -1;) {
							sb.append(new String(buffer));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dataBean.setData(sb.toString());
					
					// Turn the out file to the warc file name
					this.setOutputFilePath(
							FileHelper.getOutputFileName(dataBean.getUrl()));
					
					try {
						ar.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} while (skip != false);
			
		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
			// Write a warcinfo record with description about how this WARC
			// was made.
			try {
				InputStream is = new ByteArrayInputStream(((String)bean.getData()).
		            		getBytes(Constants.outputEnconding));
				
				ANVLRecord headers = new ANVLRecord(1);
			    //headers.addLabelValue("mietiqueta", "127.0.0.1");
			    writer.writeResourceRecord(bean.getUrl(),
			                ArchiveUtils.get14DigitDate(), Constants.outputContentType,
			                headers, is, is.available());
			
			    is.close();
			} catch (IOException e) {
				System.out.println("erroror1!!!");
				e.printStackTrace();
				throw new WriteException(e);
			} catch (Exception e) {
				System.out.println("erroror2!!!");
				e.printStackTrace();
			}
	}

	public void close() throws DSException {
		try {
			if (writer != null)
				writer.close();
			if (reader != null)
				reader.close();

			// Check if the output file is empty and remove it
			if (warc != null && warc.length() == 0) {
				warc.delete();
			}
		} catch (IOException e) {
			throw new CloseException(e);
		}
	}

}
