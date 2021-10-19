package zone.yiqing.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@Getter
@Setter
public class OssCallbackResult {

  @ApiModelProperty("文件名称")
  private String filename;
  @ApiModelProperty("文件大小")
  private String size;
  @ApiModelProperty("文件的mimeType")
  private String mimeType;
  @ApiModelProperty("图片文件的宽")
  private String width;
  @ApiModelProperty("图片文件的高")
  private String height;

}
